package silly.manage.util

/**
 * 程序目的：
 *   从命令行接收一个数，并将其转化为中文金额的大写方式
 * 例如 123.45 --> 壹佰贰拾叁元肆角伍分
 * @author LovinChan
 *
 *   看到网上有很多这样的例子程序，不过觉得很不满意。有些程序我从命名上就觉得
 * 实在是不符合规范，程序里面的算法没有让人明白得很清楚的注释，读上去觉得有
 * 点难度，可读性不强。而且很多程序还存在不少bug，随便一测就测出来了。
 *   所以本人还是决定重新写一下这个程序，并且尽量做到消除不必要的bug。这个程
 * 序我没有用什么很精妙的算法，不过用了一些Java类库中的类，像是正则表达式之类
 * 的东西。由于本人对算法不怎么在行，在做转换操作的时候用的是很笨的方法。望各位
 * 大虾海涵，呵呵。
 *
 *   程序的注释我尽量写得详细一点，如果觉得这个程序哪里有问题或者是哪里有改进的
 * 地方欢迎随时跟我交流。
 * 我的msn:egg.chenlw@gmail.com
 *    QQ:372133556(注上为什么加我就可以了)
 * 我的blog:http://hi.baidu.com/egg_chen
 * 欢迎交流
 */


fun money2Up(price: Double): String {
    if (price == 0.0) return ""
    return cleanZero(splitNum(roundString(price.toString())))
}

/**
 * 把用户输入的数以小数点为界分割开来，并调用 numFormat() 方法
 * 进行相应的中文金额大写形式的转换
 * 注：传入的这个数应该是经过 roundString() 方法进行了四舍五入操作的
 * @param s String
 * @return 转换好的中文金额大写形式的字符串
 */
private fun splitNum(s: String): String {
    // 如果传入的是空串则继续返回空串
    if ("" == s) {
        return ""
    }
    // 以小数点为界分割这个字符串
    val index = s.indexOf(".")
    // 截取并转换这个数的整数部分
    val intOnly = s.substring(0, index)
    val part1 = numFormat(1, intOnly)
    // 截取并转换这个数的小数部分
    val smallOnly = s.substring(index + 1)
    val part2 = numFormat(2, smallOnly)
    // 把转换好了的整数部分和小数部分重新拼凑一个新的字符串
    return part1 + part2
}

/**
 * 对传入的数进行四舍五入操作
 * @param s String 从命令行输入的那个数
 * @return 四舍五入后的新值
 */
private fun roundString(s: String): String {
    var s = s
    // 如果传入的是空串则继续返回空串
    if ("" == s) {
        return ""
    }
    // 将这个数转换成 double 类型，并对其进行四舍五入操作
    var d = java.lang.Double.parseDouble(s)
    // 此操作作用在小数点后两位上
    d = (d * 100 + 0.5) / 100
    // 将 d 进行格式化
    s = java.text.DecimalFormat("##0.000").format(d)
    // 以小数点为界分割这个字符串
    val index = s.indexOf(".")
    // 这个数的整数部分
    val intOnly = s.substring(0, index)
    // 规定数值的最大长度只能到万亿单位，否则返回 "0"
    if (intOnly.length > 13) {
        println("输入数据过大！（整数部分最多13位！）")
        return ""
    }
    // 这个数的小数部分
    val smallOnly = s.substring(index + 1)
    // 如果小数部分大于两位，只截取小数点后两位
    if (smallOnly.length > 2) {
        val roundSmall = smallOnly.substring(0, 2)
        // 把整数部分和新截取的小数部分重新拼凑这个字符串
        s = "$intOnly.$roundSmall"
    }
    return s
}

/**
 * 把传入的数转换为中文金额大写形式
 * @param flag int 标志位，1 表示转换整数部分，0 表示转换小数部分
 * @param s String 要转换的字符串
 * @return 转换好的带单位的中文金额大写形式
 */
private fun numFormat(flag: Int, s: String): String {
    val sLength = s.length
    // 货币大写形式
    val bigLetter = arrayOf("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖")
    // 货币单位
    val unit = arrayOf("元", "拾", "佰", "仟", "万",
            // 拾万位到仟万位
            "拾", "佰", "仟",
            // 亿位到万亿位
            "亿", "拾", "佰", "仟", "万")
    val small = arrayOf("分", "角")
    // 用来存放转换后的新字符串
    var newS = ""
    // 逐位替换为中文大写形式
    for (i in 0 until sLength) {
        if (flag == 1) {
            // 转换整数部分为中文大写形式（带单位）
            newS = newS + bigLetter[s[i].toInt() - 48] + unit[sLength - i - 1]
        } else if (flag == 2) {
            // 转换小数部分（带单位）
            newS = newS + bigLetter[s[i].toInt() - 48] + small[sLength - i - 1]
        }
    }
    return newS
}

/**
 * 把已经转换好的中文金额大写形式加以改进，清理这个字
 * 符串里面多余的零，让这个字符串变得更加可观
 * 注：传入的这个数应该是经过 splitNum() 方法进行处理，这个字
 * 符串应该已经是用中文金额大写形式表示的
 * @param s String 已经转换好的字符串
 * @return 改进后的字符串
 */
private fun cleanZero(s: String): String {
    var s = s
    // 如果传入的是空串则继续返回空串
    if ("" == s) {
        return ""
    }
    // 如果用户开始输入了很多 0 去掉字符串前面多余的'零'，使其看上去更符合习惯
    while (s[0] == '零') {
        // 将字符串中的 "零" 和它对应的单位去掉
        s = s.substring(2)
        // 如果用户当初输入的时候只输入了 0，则只返回一个 "零"
        if (s.length == 0) {
            return "零"
        }
    }
    // 字符串中存在多个'零'在一起的时候只读出一个'零'，并省略多余的单位
    /* 由于本人对算法的研究太菜了，只能用4个正则表达式去转换了，各位大虾别介意哈... */
    val regex1 = arrayOf("零仟", "零佰", "零拾")
    val regex2 = arrayOf("零亿", "零万", "零元")
    val regex3 = arrayOf("亿", "万", "元")
    val regex4 = arrayOf("零角", "零分")
    // 第一轮转换把 "零仟", 零佰","零拾"等字符串替换成一个"零"
    for (i in 0..2) {
        s = s.replace(regex1[i].toRegex(), "零")
    }
    // 第二轮转换考虑 "零亿","零万","零元"等情况
    // "亿","万","元"这些单位有些情况是不能省的，需要保留下来
    for (i in 0..2) {
        // 当第一轮转换过后有可能有很多个零叠在一起
        // 要把很多个重复的零变成一个零
        s = s.replace("零零零".toRegex(), "零")
        s = s.replace("零零".toRegex(), "零")
        s = s.replace(regex2[i].toRegex(), regex3[i])
    }
    // 第三轮转换把"零角","零分"字符串省略
    for (i in 0..1) {
        s = s.replace(regex4[i].toRegex(), "")
    }
    // 当"万"到"亿"之间全部是"零"的时候，忽略"亿万"单位，只保留一个"亿"
    s = s.replace("亿万".toRegex(), "亿")
    return s
}
