package lfs.ktinsim.utils

import lfs.ktinsim.trim
import lfs.ktinsim.trimBefore
import java.nio.ByteBuffer
import java.nio.charset.Charset

object LFSTextUtils {

    val availableCharsets = Charset.availableCharsets()

    private val codepages = mapOf<Byte, String>(
        'L'.code.toByte() to "windows-1252",
        'G'.code.toByte() to "windows-1253",
        'C'.code.toByte() to "windows-1251",
        'E'.code.toByte() to "windows-1250",
        'T'.code.toByte() to "windows-1254",
        'B'.code.toByte() to "windows-1257",
        'J'.code.toByte() to "x-MS932_0213",
        'H'.code.toByte() to "x-MS950-HKSCS",
        'S'.code.toByte() to "x-mswin-936",
        'K'.code.toByte() to "x-windows-949",
        '8'.code.toByte() to "windows-1252"
    )

    val specialSymbolsDecodeMap = mapOf<String, Byte>(
        "^v" to '|'.code.toByte(),
        "^a" to '*'.code.toByte(),
        "^c" to ':'.code.toByte(),
        "^d" to '\\'.code.toByte(),
        "^s" to '/'.code.toByte(),
        "^q" to '?'.code.toByte(),
        "^t" to '\"'.code.toByte(),
        "^l" to '<'.code.toByte(),
        "^r" to '>'.code.toByte(),
        "^h" to '#'.code.toByte(),
        "^^" to '^'.code.toByte(),
    )

    val specialSymbolsEncodeMap = mapOf<Int, Byte>(
        '|'.code to 'v'.code.toByte(),
        '*'.code to 'a'.code.toByte(),
        ':'.code to 'c'.code.toByte(),
        '\''.code to 'd'.code.toByte(),
        '/'.code to 's'.code.toByte(),
        '?'.code to 'q'.code.toByte(),
        '\"'.code to 't'.code.toByte(),
        '<'.code to 'l'.code.toByte(),
        '>'.code to 'r'.code.toByte(),
        '#'.code to 'h'.code.toByte(),
        '^'.code to '^'.code.toByte(),
    )

    fun isMultiByte(codepage: Byte, ch: Byte): Boolean {
        return when (codepage) {
            'J'.code.toByte() -> (ch in 0x81..0x9f) || (ch in 0xe0..0xfc)
            'H'.code.toByte(),
            'S'.code.toByte(),
            'K'.code.toByte() -> ch in 0x81..0xfe
            else -> false
        }
    }

    fun ByteArray.toUTF8String(i: Int, size: Int) : String {
        val arr = this.sliceArray(i until i+size).trim()
        val buffer = ByteBuffer.wrap(arr)

        var codepage = 'L'.code.toByte()
        val stringBuilder = StringBuilder()
        var tempBuffer = ByteBuffer.allocate(arr.size)

        var charset = Charsets.US_ASCII
        var i = 0
        while (i < arr.size) {
            if (isMultiByte(codepage, buffer[i])) {
                repeat(2) {
                    tempBuffer.put(buffer[i])
                    i++
                }
            } else if (buffer[i].toInt() == '^'.code) {
                if (buffer[i+1] in codepages.keys && codepages[buffer[i+1]] in availableCharsets.keys) {
                    stringBuilder.append(tempBuffer.array().trim().toString(charset))
                    tempBuffer = ByteBuffer.allocate(arr.size)
                    codepage = buffer[i+1]
                    charset = availableCharsets[codepages[buffer[i+1]]]!!
                    i += 2
                } else {
                    repeat(2) {
                        tempBuffer.put(buffer[i])
                        i++
                    }
                }
            } else {
                tempBuffer.put(buffer[i])
                i++
            }
        }
        stringBuilder.append(tempBuffer.array().trim().toString(charset))
        return stringBuilder.toString()
    }

    fun String.toLFSBytes() : ByteArray {
        val buffer = ByteBuffer.allocate(this.length * 4)
        var i = 0
        var currentSymbolMap: Map<Int, Int>? = null

        while (i < this.length) {
            val codePoint = this.codePointAt(i)
            if (codePoint <= 127) {
                if (codePoint in specialSymbolsEncodeMap) {
                    buffer.put('^'.code.toByte())
                    buffer.put(specialSymbolsEncodeMap[codePoint]!!)
                } else {
                    buffer.put(codePoint.toByte())
                }
                i++
                continue
            }

            if (currentSymbolMap != null && codePoint in currentSymbolMap.keys) {
                // skip here
            } else {
                if (codePoint in win1252Map.keys) {
                    currentSymbolMap = win1252Map
                    buffer.put('^'.code.toByte())
                    buffer.put('L'.code.toByte())
                } else if (codePoint in win1253Map.keys) {
                    currentSymbolMap = win1253Map
                    buffer.put('^'.code.toByte())
                    buffer.put('G'.code.toByte())
                } else if (codePoint in win1251Map.keys) {
                    currentSymbolMap = win1251Map
                    buffer.put('^'.code.toByte())
                    buffer.put('C'.code.toByte())
                } else if (codePoint in win1254Map.keys) {
                    currentSymbolMap = win1254Map
                    buffer.put('^'.code.toByte())
                    buffer.put('E'.code.toByte())
                } else if (codePoint in win1250Map.keys) {
                    currentSymbolMap = win1250Map
                    buffer.put('^'.code.toByte())
                    buffer.put('T'.code.toByte())
                } else if (codePoint in win1253Map.keys) {
                    currentSymbolMap = win1253Map
                    buffer.put('^'.code.toByte())
                    buffer.put('G'.code.toByte())
                } else if (codePoint in win1257Map.keys) {
                    currentSymbolMap = win1257Map
                    buffer.put('^'.code.toByte())
                    buffer.put('B'.code.toByte())
                } else {
                    // TODO (CJK support)
                }
            }

            buffer.put(
                currentSymbolMap?.get(codePoint)!!.toBytes()
            )
            i++
        }

        return buffer.array()
    }
}

private fun Int.toBytes(): ByteArray {
    return ByteArray(4) {
        (this and (0xFF shl (3-it)*8).toInt() shr (3-it)*8).toByte()
    }.trimBefore()
}

// Latin
val win1252Map = mapOf(
    8364 to 128,
    8230 to 133,
    8224 to 134,
    8225 to 135,
    710 to 136,
    352 to 138,
    8249 to 139,
    338 to 140,
    381 to 142,
    8216 to 145,
    8217 to 146,
    8220 to 147,
    8221 to 148,
    8226 to 149,
    8211 to 150,
    8212 to 151,
    732 to 152,
    8482 to 153,
    353 to 154,
    8250 to 155,
    339 to 156,
    382 to 158,
    376 to 159,
    160 to 160,
    161 to 161,
    162 to 162,
    163 to 163,
    165 to 165,
    166 to 166,
    167 to 167,
    168 to 168,
    169 to 169,
    170 to 170,
    171 to 171,
    172 to 172,
    173 to 173,
    174 to 174,
    175 to 175,
    176 to 176,
    177 to 177,
    178 to 178,
    179 to 179,
    180 to 180,
    181 to 181,
    182 to 182,
    183 to 183,
    184 to 184,
    185 to 185,
    186 to 186,
    187 to 187,
    191 to 191,
    192 to 192,
    193 to 193,
    194 to 194,
    195 to 195,
    196 to 196,
    197 to 197,
    198 to 198,
    199 to 199,
    200 to 200,
    201 to 201,
    202 to 202,
    203 to 203,
    204 to 204,
    205 to 205,
    206 to 206,
    207 to 207,
    208 to 208,
    209 to 209,
    210 to 210,
    211 to 211,
    212 to 212,
    213 to 213,
    214 to 214,
    215 to 215,
    216 to 216,
    217 to 217,
    218 to 218,
    219 to 219,
    220 to 220,
    221 to 221,
    222 to 222,
    223 to 223,
    224 to 224,
    225 to 225,
    226 to 226,
    227 to 227,
    228 to 228,
    229 to 229,
    230 to 230,
    231 to 231,
    232 to 232,
    233 to 233,
    234 to 234,
    235 to 235,
    236 to 236,
    237 to 237,
    238 to 238,
    239 to 239,
    240 to 240,
    241 to 241,
    242 to 242,
    243 to 243,
    244 to 244,
    245 to 245,
    246 to 246,
    247 to 247,
    248 to 248,
    249 to 249,
    250 to 250,
    251 to 251,
    252 to 252,
    253 to 253,
    254 to 254,
    255 to 255,
)

// Cyrillic
val win1251Map = mapOf(
    // ЂЃ ѓ …†‡€ Љ‹ЊЌЋЏђ‘’“”•–— ™љ›њќћџ ЎўЈ Ґ¦§Ё©Є«¬­®Ї°±Ііґµ¶·ё№є»јЅѕї
    1026 to 128, // Ђ
    1027 to 129, // Ѓ
    1107 to 131, // ѓ
    8230 to 133, // …
    8224 to 134, // †
    8225 to 135, // ‡
    8364 to 136, //
    1033 to 138, // Ђ
    8249 to 139, // Ђ
    1034 to 140, // Ђ
    1036 to 141, // Ђ
    1035 to 142, // Ђ
    1039 to 143, // Ђ
    1106 to 144, // Ђ
    8216 to 145, // Ђ
    8217 to 146, // Ђ
    8220 to 147, // Ђ
    8221 to 148, // Ђ
    8226 to 149, // Ђ
    8211 to 150, // Ђ
    8212 to 151, // Ђ
    8482 to 153, // Ђ
    1113 to 154, // Ђ
    8250 to 155, // Ђ
    1114 to 156, // Ђ
    1116 to 157, // Ђ
    1115 to 158, // Ђ
    1119 to 159, // Ђ
    160 to 160, // Ђ
    1038 to 161, // Ђ
    1118 to 162, // Ђ
    1032 to 163, // Ђ
    1168 to 165, // Ђ
    166 to 166, // Ђ
    167 to 167, // Ђ
    1025 to 168, // Ђ
    169 to 169, // Ђ
    1028 to 170, // Ђ
    171 to 171, // Ђ
    172 to 172, // Ђ
    173 to 173, // Ђ
    174 to 174, // Ђ
    1031 to 175, // Ђ
    176 to 176, // Ђ
    177 to 177, // Ђ
    1030 to 178, // Ђ
    1110 to 179, // Ђ
    1169 to 180, // Ђ
    181 to 181, // Ђ
    182 to 182, // Ђ
    183 to 183, // Ђ
    1105 to 184, // Ђ
    8470 to 185, // Ђ
    1108 to 186, // Ђ
    187 to 187, // Ђ
    1112 to 188, // Ђ
    1029 to 189, // Ђ
    1109 to 190, // Ђ
    1111 to 191, // Ђ
    1040 to 192,
    1041 to 193,
    1042 to 194,
    1043 to 195,
    1044 to 196,
    1045 to 197,
    1046 to 198,
    1047 to 199,
    1048 to 200,
    1049 to 201,
    1050 to 202,
    1051 to 203,
    1052 to 204,
    1053 to 205,
    1054 to 206,
    1055 to 207,
    1056 to 208,
    1057 to 209,
    1058 to 210,
    1059 to 211,
    1060 to 212,
    1061 to 213,
    1062 to 214,
    1063 to 215,
    1064 to 216,
    1065 to 217,
    1066 to 218,
    1067 to 219,
    1068 to 220,
    1069 to 221,
    1070 to 222,
    1071 to 223,
    1072 to 224,
    1073 to 225,
    1074 to 226,
    1075 to 227,
    1076 to 228,
    1077 to 229,
    1078 to 230,
    1079 to 231,
    1080 to 232,
    1081 to 233,
    1082 to 234,
    1083 to 235,
    1084 to 236,
    1085 to 237,
    1086 to 238,
    1087 to 239,
    1088 to 240,
    1089 to 241,
    1090 to 242,
    1091 to 243,
    1092 to 244,
    1093 to 245,
    1094 to 246,
    1095 to 247,
    1096 to 248,
    1097 to 249,
    1098 to 250,
    1099 to 251,
    1100 to 252,
    1101 to 253,
    1102 to 254,
    1103 to 255,
)

// Greek
val win1253Map = mapOf(
    8364 to 128,
    8230 to 133,
    8224 to 134,
    8225 to 135,
    8249 to 139,
    8216 to 145,
    8217 to 146,
    8220 to 147,
    8221 to 148,
    8226 to 149,
    8211 to 150,
    8212 to 151,
    8482 to 153,
    8250 to 155,
    160 to 160,
    901 to 161,
    902 to 162,
    163 to 163,
    165 to 165,
    166 to 166,
    167 to 167,
    168 to 168,
    169 to 169,
    171 to 171,
    172 to 172,
    173 to 173,
    174 to 174,
    8213 to 175,
    176 to 176,
    177 to 177,
    178 to 178,
    179 to 179,
    900 to 180,
    181 to 181,
    182 to 182,
    183 to 183,
    904 to 184,
    905 to 185,
    906 to 186,
    187 to 187,
    908 to 188,
    910 to 190,
    911 to 191,
    192 to 192,
    193 to 193,
    194 to 194,
    195 to 195,
    196 to 196,
    197 to 197,
    198 to 198,
    199 to 199,
    200 to 200,
    201 to 201,
    202 to 202,
    203 to 203,
    204 to 204,
    205 to 205,
    206 to 206,
    207 to 207,
    208 to 208,
    209 to 209,
    210 to 210,
    211 to 211,
    212 to 212,
    213 to 213,
    214 to 214,
    215 to 215,
    216 to 216,
    217 to 217,
    218 to 218,
    219 to 219,
    220 to 220,
    221 to 221,
    222 to 222,
    223 to 223,
    224 to 224,
    225 to 225,
    226 to 226,
    227 to 227,
    228 to 228,
    229 to 229,
    230 to 230,
    231 to 231,
    232 to 232,
    233 to 233,
    234 to 234,
    235 to 235,
    236 to 236,
    237 to 237,
    238 to 238,
    239 to 239,
    240 to 240,
    241 to 241,
    242 to 242,
    243 to 243,
    244 to 244,
    245 to 245,
    246 to 246,
    247 to 247,
    248 to 248,
    249 to 249,
    250 to 250,
    251 to 251,
    252 to 252,
    253 to 253,
    254 to 254,
    255 to 255,
)

// Central european
val win1254Map = mapOf(
    8364 to 128,
    8230 to 133,
    8224 to 134,
    8225 to 135,
    710 to 136,
    352 to 138,
    8249 to 139,
    338 to 140,
    8216 to 145,
    8217 to 146,
    8220 to 147,
    8221 to 148,
    8226 to 149,
    8211 to 150,
    8212 to 151,
    732 to 152,
    8482 to 153,
    353 to 154,
    8250 to 155,
    339 to 156,
    376 to 159,
    160 to 160,
    161 to 161,
    162 to 162,
    163 to 163,
    165 to 165,
    166 to 166,
    167 to 167,
    168 to 168,
    169 to 169,
    170 to 170,
    171 to 171,
    172 to 172,
    173 to 173,
    174 to 174,
    175 to 175,
    176 to 176,
    177 to 177,
    178 to 178,
    179 to 179,
    180 to 180,
    181 to 181,
    182 to 182,
    183 to 183,
    184 to 184,
    185 to 185,
    186 to 186,
    187 to 187,
    191 to 191,
    192 to 192,
    193 to 193,
    194 to 194,
    195 to 195,
    196 to 196,
    197 to 197,
    198 to 198,
    199 to 199,
    200 to 200,
    201 to 201,
    202 to 202,
    203 to 203,
    204 to 204,
    205 to 205,
    206 to 206,
    207 to 207,
    286 to 208,
    209 to 209,
    210 to 210,
    211 to 211,
    212 to 212,
    213 to 213,
    214 to 214,
    215 to 215,
    216 to 216,
    217 to 217,
    218 to 218,
    219 to 219,
    220 to 220,
    304 to 221,
    350 to 222,
    223 to 223,
    224 to 224,
    225 to 225,
    226 to 226,
    227 to 227,
    228 to 228,
    229 to 229,
    230 to 230,
    231 to 231,
    232 to 232,
    233 to 233,
    234 to 234,
    235 to 235,
    236 to 236,
    237 to 237,
    238 to 238,
    239 to 239,
    287 to 240,
    241 to 241,
    242 to 242,
    243 to 243,
    244 to 244,
    245 to 245,
    246 to 246,
    247 to 247,
    248 to 248,
    249 to 249,
    250 to 250,
    251 to 251,
    252 to 252,
    305 to 253,
    351 to 254,
    255 to 255,
)

// Turkish
val win1250Map = mapOf(
    8364 to 128,
    8230 to 133,
    8224 to 134,
    8225 to 135,
    8240 to 137,
    352 to 138,
    8249 to 139,
    346 to 140,
    356 to 141,
    381 to 142,
    377 to 143,
    8216 to 145,
    8217 to 146,
    8220 to 147,
    8221 to 148,
    8226 to 149,
    8211 to 150,
    8212 to 151,
    8482 to 153,
    353 to 154,
    8250 to 155,
    347 to 156,
    357 to 157,
    382 to 158,
    378 to 159,
    160 to 160,
    711 to 161,
    728 to 162,
    321 to 163,
    260 to 165,
    166 to 166,
    167 to 167,
    168 to 168,
    169 to 169,
    350 to 170,
    171 to 171,
    172 to 172,
    173 to 173,
    174 to 174,
    379 to 175,
    176 to 176,
    177 to 177,
    731 to 178,
    322 to 179,
    180 to 180,
    181 to 181,
    182 to 182,
    183 to 183,
    184 to 184,
    261 to 185,
    351 to 186,
    187 to 187,
    317 to 188,
    733 to 189,
    318 to 190,
    380 to 191,
    340 to 192,
    193 to 193,
    194 to 194,
    258 to 195,
    196 to 196,
    313 to 197,
    262 to 198,
    199 to 199,
    268 to 200,
    201 to 201,
    280 to 202,
    203 to 203,
    282 to 204,
    205 to 205,
    206 to 206,
    270 to 207,
    272 to 208,
    323 to 209,
    327 to 210,
    211 to 211,
    212 to 212,
    336 to 213,
    214 to 214,
    215 to 215,
    344 to 216,
    366 to 217,
    218 to 218,
    368 to 219,
    220 to 220,
    221 to 221,
    354 to 222,
    223 to 223,
    341 to 224,
    225 to 225,
    226 to 226,
    259 to 227,
    228 to 228,
    314 to 229,
    263 to 230,
    231 to 231,
    269 to 232,
    233 to 233,
    281 to 234,
    235 to 235,
    283 to 236,
    237 to 237,
    238 to 238,
    271 to 239,
    273 to 240,
    324 to 241,
    328 to 242,
    243 to 243,
    244 to 244,
    337 to 245,
    246 to 246,
    247 to 247,
    345 to 248,
    367 to 249,
    250 to 250,
    369 to 251,
    252 to 252,
    253 to 253,
    355 to 254,
    729 to 255,
)

// Baltic
val win1257Map = mapOf(
    8364 to 128,
    8230 to 133,
    8224 to 134,
    8225 to 135,
    8249 to 139,
    168 to 141,
    711 to 142,
    184 to 143,
    8216 to 145,
    8217 to 146,
    8220 to 147,
    8221 to 148,
    8226 to 149,
    8211 to 150,
    8212 to 151,
    8482 to 153,
    8250 to 155,
    175 to 157,
    731 to 158,
    160 to 160,
    162 to 162,
    163 to 163,
    166 to 166,
    167 to 167,
    216 to 168,
    169 to 169,
    342 to 170,
    171 to 171,
    172 to 172,
    173 to 173,
    174 to 174,
    198 to 175,
    176 to 176,
    177 to 177,
    178 to 178,
    179 to 179,
    180 to 180,
    181 to 181,
    182 to 182,
    183 to 183,
    248 to 184,
    185 to 185,
    343 to 186,
    187 to 187,
    230 to 191,
    260 to 192,
    302 to 193,
    256 to 194,
    262 to 195,
    196 to 196,
    197 to 197,
    280 to 198,
    274 to 199,
    268 to 200,
    201 to 201,
    377 to 202,
    278 to 203,
    290 to 204,
    310 to 205,
    298 to 206,
    315 to 207,
    352 to 208,
    323 to 209,
    325 to 210,
    211 to 211,
    332 to 212,
    213 to 213,
    214 to 214,
    215 to 215,
    370 to 216,
    321 to 217,
    346 to 218,
    362 to 219,
    220 to 220,
    379 to 221,
    381 to 222,
    223 to 223,
    261 to 224,
    303 to 225,
    257 to 226,
    263 to 227,
    228 to 228,
    229 to 229,
    281 to 230,
    275 to 231,
    269 to 232,
    233 to 233,
    378 to 234,
    279 to 235,
    291 to 236,
    311 to 237,
    299 to 238,
    316 to 239,
    353 to 240,
    324 to 241,
    326 to 242,
    243 to 243,
    333 to 244,
    245 to 245,
    246 to 246,
    247 to 247,
    371 to 248,
    322 to 249,
    347 to 250,
    363 to 251,
    252 to 252,
    380 to 253,
    382 to 254,
    729 to 255,
)
