import java.io.* // ktlint-disable no-wildcard-imports

fun main() {
    try {
        val reader = BufferedReader(FileReader("input.txt"))
        var line: String?
        val section = "\t"
        val subsection = "\t\t"
        val subsubsection = "\t\t\t"
        var nextPerson = false
        var nextFamily = false
        val xmlBuilder = StringBuilder("<people>\n")

        while (reader.readLine().also { line = it } != null) {
            val tokens = line!!.split("\\|".toRegex()).toTypedArray()
            if (tokens.isNotEmpty()) {
                when (tokens[0]) {
                    "P" -> {
                        if (nextPerson) {
                            if (nextFamily) {
                                xmlBuilder.append(subsection)
                                xmlBuilder.append("</family>\n")
                                nextFamily = false
                            }
                            xmlBuilder.append(section)
                            xmlBuilder.append("</person>\n")
                        }
                        xmlBuilder.append(section)
                        xmlBuilder.append("<person>\n")

                        if (tokens.size > 1 && tokens[1].isNotEmpty()) {
                            xmlBuilder.append(subsection)
                            xmlBuilder.append("<firstname>").append(tokens[1]).append("</firstname>\n")
                        }

                        if (tokens.size > 2 && tokens[2].isNotEmpty()) {
                            xmlBuilder.append(subsection)
                            xmlBuilder.append("<lastname>").append(tokens[2]).append("</lastname>\n")
                        }
                        nextPerson = true
                    }
                    "T" -> {
                        if (nextFamily) {
                            xmlBuilder.append(section)
                        }
                        xmlBuilder.append(subsection)
                        xmlBuilder.append("<phone>\n")

                        if (tokens.size > 1 && tokens[1].startsWith("07")) {
                            if (nextFamily) {
                                xmlBuilder.append(section)
                            }
                            xmlBuilder.append(subsubsection)
                            xmlBuilder.append("<mobile>").append(tokens[1]).append("</mobile>\n")
                        } else if (tokens.size > 1 && tokens[1].startsWith("04")) {
                            if (nextFamily) {
                                xmlBuilder.append(section)
                            }
                            xmlBuilder.append(subsubsection)
                            xmlBuilder.append("<landline>").append(tokens[2]).append("</landline>\n")
                        }

                        if (tokens.size > 2 && tokens[2].startsWith("04")) {
                            if (nextFamily) {
                                xmlBuilder.append(section)
                            }
                            xmlBuilder.append(subsubsection)
                            xmlBuilder.append("<landline>").append(tokens[2]).append("</landline>\n")
                        }

                        if (nextFamily) {
                            xmlBuilder.append(section)
                        }
                        xmlBuilder.append(subsection)
                        xmlBuilder.append("</phone>\n")
                    }
                    "A" -> {
                        if (nextFamily) {
                            xmlBuilder.append(section)
                        }
                        xmlBuilder.append(subsection)
                        xmlBuilder.append("<address>\n")

                        if (tokens.size > 1 && tokens[1].isNotEmpty()) {
                            if (nextFamily) {
                                xmlBuilder.append(section)
                            }
                            xmlBuilder.append(subsubsection)
                            xmlBuilder.append("<street>").append(tokens[1]).append("</street>\n")
                        }

                        if (tokens.size > 2 && tokens[2].isNotEmpty()) {
                            if (nextFamily) {
                                xmlBuilder.append(section)
                            }
                            xmlBuilder.append(subsubsection)
                            xmlBuilder.append("<city>").append(tokens[2]).append("</city>\n")
                        }

                        if (tokens.size > 3 && tokens[3].isNotEmpty()) {
                            if (nextFamily) {
                                xmlBuilder.append(section)
                            }
                            xmlBuilder.append(subsubsection)
                            xmlBuilder.append("<postcode>").append(tokens[3]).append("</postcode>\n")
                        }

                        if (nextFamily) {
                            xmlBuilder.append(section)
                        }
                        xmlBuilder.append(subsection)
                        xmlBuilder.append("</address>\n")
                    }
                    "F" -> {
                        if (nextFamily) {
                            xmlBuilder.append(subsection)
                            xmlBuilder.append("</family>\n")
                        }
                        xmlBuilder.append(subsection)
                        xmlBuilder.append("<family>\n")

                        if (tokens.size > 1 && tokens[1].isNotEmpty()) {
                            xmlBuilder.append(subsubsection)
                            xmlBuilder.append("<name>").append(tokens[1]).append("</name>\n")
                        }

                        if (tokens.size > 2 && tokens[2].isNotEmpty()) {
                            xmlBuilder.append(subsubsection)
                            xmlBuilder.append("<born>").append(tokens[2]).append("</born>\n")
                        }

                        nextFamily = true
                    }
                }
            }
        }
        xmlBuilder.append(section)
        xmlBuilder.append("</person>\n")
        xmlBuilder.append("</people>")

        val output = "output.xml"

        try {
            BufferedWriter(FileWriter(output)).use { writer ->
                writer.write(xmlBuilder.toString())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        reader.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
