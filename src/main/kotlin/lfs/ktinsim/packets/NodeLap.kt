package lfs.ktinsim.packets

import lfs.ktinsim.getUShortAt

/*

struct NodeLap // Car info in 6 bytes - there is an array of these in the NLP (below)
{
	word	Node;		// current path node
	word	Lap;		// current lap
	byte	PLID;		// player's unique id
	byte	Position;	// current race position: 0 = unknown, 1 = leader, etc...
};
 */

data class NodeLap(
    val node: UShort,
    val lap: UShort,
    val playerId: UByte,
    val position: UByte
) {
    companion object;

    constructor(data: ByteArray) : this(
        node = data.getUShortAt(0),
        lap = data.getUShortAt(2),
        playerId = data[4].toUByte(),
        position = data[5].toUByte()
    )
}
