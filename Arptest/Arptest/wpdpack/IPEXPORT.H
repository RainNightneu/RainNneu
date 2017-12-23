// Don't fragment this packet.

//
// Supported IP Option Types.
//
// These types define the options which may be used in the OptionsData field
// of the ip_option_information structure.  See RFC 791 for a complete
// description of each.
//
#define IP_OPT_EOL      0          // End of list option
#define IP_OPT_NOP      1          // No operation
#define IP_OPT_SECURITY 0x82       // Security option
#define IP_OPT_LSRR     0x83       // Loose source route
#define IP_OPT_SSRR     0x89       // Strict source route
#define IP_OPT_RR       0x7        // Record route
#define IP_OPT_TS       0x44       // Timestamp
#define IP_OPT_SID      0x88       // Stream ID (obsolete)
#define IP_OPT_ROUTER_ALERT 0x94  // Router Alert Option

#define MAX_OPT_SIZE    40         // Maximum length of IP options in bytes

#ifdef CHICAGO

// Ioctls code exposed by Memphis tcpip stack.
// For NT these ioctls are define in ntddip.h  (private\inc)

#define IOCTL_IP_RTCHANGE_NOTIFY_REQUEST   101
#define IOCTL_IP_ADDCHANGE_NOTIFY_REQUEST  102
#define IOCTL_ARP_SEND_REQUEST             103
#define IOCTL_IP_INTERFACE_INFO            104
#define IOCTL_IP_GET_BEST_INTERFACE        105
#define IOCTL_IP_UNIDIRECTIONAL_ADAPTER_ADDRESS        106

#endif


#endif // IP_EXPORT_INCLUDED

