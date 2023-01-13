package lfs.ktinsim.packets

enum class MessageSound: ByteEnum {
    SILENT,
    MESSAGE,
    SYSMESSAGE,
    INVALIDKEY,
    ERROR,
    NUM;
}