package com.daijie.ksyllabusapp.data

/**
 * Created by daidaijie on 17-10-19.
 */
data class OACompany(
        val id: Int,
        val name: String,
        val shortName: String
) {
    companion object {
        val oaCompanies: List<OACompany> by lazy {
            listOf(
                    OACompany(0, "全部", "全部"),
                    OACompany(35, "理学院", "理学院"),
                    OACompany(40, "长江新闻学院", "新闻"),
                    OACompany(42, "体育部", "体育部"),
                    OACompany(44, "网络与信息中心", "网络"),
                    OACompany(46, "学报编辑部", "学编部"),
                    OACompany(101, "发展规划办", "发规办"),
                    OACompany(121, "学生创业中心", "学创"),
                    OACompany(207, "弘毅书院", "弘毅"),
                    OACompany(26, "财务处", "财务处"),
                    OACompany(202, "知行书院", "知行"),
                    OACompany(28, "科研处", "科研处"),
                    OACompany(32, "继续教育学院", "继教院"),
                    OACompany(36, "文学院", "文学院"),
                    OACompany(39, "长江艺术学院", "艺术"),
                    OACompany(73, "研究生学院负责人", "研负责"),
                    OACompany(201, "思源书院", "思源"),
                    OACompany(204, "知行书院办公室", "知行办"),
                    OACompany(33, "资源管理处", "资管处"),
                    OACompany(37, "法学院", "法学院"),
                    OACompany(41, "社科部", "社科部"),
                    OACompany(43, "图书馆", "图书馆"),
                    OACompany(69, "商学院负责人", "商负责"),
                    OACompany(50, "宣传部", "宣传部"),
                    OACompany(22, "党政办/国际交流处", "党政办"),
                    OACompany(25, "人事处", "人事处"),
                    OACompany(74, "ELC负责人", "ELC负责"),
                    OACompany(141, "教师发展与教育评估中心", "教中心"),
                    OACompany(24, "监察审计处", "监审处"),
                    OACompany(48, "中心实验室", "中实验"),
                    OACompany(71, "新闻学院负责人", "新闻责"),
                    OACompany(47, "ELC", "ELC"),
                    OACompany(1, "校领导", "校领导"),
                    OACompany(38, "商学院", "商学院"),
                    OACompany(47, "ELC", "ELC"),
                    OACompany(68, "法学院负责人", "法负责"),
                    OACompany(23, "组织统战部", "组统部"),
                    OACompany(27, "教务处", "教务处"),
                    OACompany(29, "研究生学院", "研究生"),
                    OACompany(30, "学生处（团委）", "学生处"),
                    OACompany(70, "艺术学院负责人", "艺术责"),
                    OACompany(72, "高教所负责人", "高教责"),
                    OACompany(181, "汕头大学体育园", "汕体园"),
                    OACompany(31, "校工会", "校工会"),
                    OACompany(67, "工学院负责人", "工负责"),
                    OACompany(34, "工学院", "工学院"),
                    OACompany(45, "高等教育研究所", "高教所"),
                    OACompany(49, "至诚书院", "至诚"),
                    OACompany(61, "基金会", "基金会")
            )
        }

        val oaCompaniesMap: Map<Int, OACompany> by lazy {
            val tempMap = mutableMapOf<Int, OACompany>()
            oaCompanies.forEach {
                tempMap.put(it.id, it)
            }
            tempMap.toMap()
        }

        val oaNames: Array<String> by lazy {
            oaCompanies.map {
                it.name
            }.toTypedArray()
        }
    }
}