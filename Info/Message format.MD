**When x is an odd number:** 

When message x is being sent to the server, message x+1 is expected to be received.

For example, if message 0x4307 is sent to the server, the client expects message 0x4308 to be sent back.

Do notice that the protocol uses decimal-hexdecimal representation.

What I mean by that is that if the client sends message 0x4309 (17161), it won't be expecting message 0x430A but instead 0x4310 (17168).

**When x is an even number:**

Not sure yet. It seems like you need to send a different message (not x+1).

**Message format:**

The bytes are in little-endian format.

int length (the entire packet's length. This field's length is included)
int messageType
int validator // when a message is received, this value is compared to 0x2B1C. if it's anything else, the message is thrown.
int checksum // the checksum of the message
int state // the state of the message. not sure what's its uses, but it needs to be calculated each time
