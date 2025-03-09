#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <jni.h>
#include <sys/socket.h>
#include <netinet/ip_icmp.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <sys/time.h>
#include <errno.h>

// Compute checksum for ICMP packet
unsigned short checksum(void *b, int len) {
    unsigned short *buf = b;
    unsigned int sum = 0;
    unsigned short result;

    for (sum = 0; len > 1; len -= 2)
        sum += *buf++;
    if (len == 1)
        sum += *(unsigned char *)buf;
    
    sum = (sum >> 16) + (sum & 0xFFFF);
    sum += (sum >> 16);
    result = ~sum;
    return result;
}

// JNI Method to send an ICMP Echo Request
JNIEXPORT jboolean JNICALL Java_RawICMPJNI_sendICMPPing(JNIEnv *env, jobject obj, jstring ipAddress) {
    const char *target_ip = (*env)->GetStringUTFChars(env, ipAddress, 0);
    int sockfd = socket(AF_INET, SOCK_RAW, IPPROTO_ICMP);

    if (sockfd < 0) {
        perror("Socket creation failed");
        return JNI_FALSE;
    }

    struct sockaddr_in dest;
    memset(&dest, 0, sizeof(dest));
    dest.sin_family = AF_INET;
    inet_pton(AF_INET, target_ip, &dest.sin_addr);

    struct icmp icmp_packet;
    memset(&icmp_packet, 0, sizeof(icmp_packet));
    icmp_packet.icmp_type = ICMP_ECHO;
    icmp_packet.icmp_code = 0;
    icmp_packet.icmp_id = getpid();
    icmp_packet.icmp_seq = 1;
    icmp_packet.icmp_cksum = checksum(&icmp_packet, sizeof(icmp_packet));

    if (sendto(sockfd, &icmp_packet, sizeof(icmp_packet), 0, (struct sockaddr *)&dest, sizeof(dest)) <= 0) {
        perror("ICMP send failed");
        close(sockfd);
        return JNI_FALSE;
    }

    close(sockfd);
    return JNI_TRUE;
}
