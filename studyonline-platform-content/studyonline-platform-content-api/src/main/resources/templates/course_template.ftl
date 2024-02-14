<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/static/img/asset-favicon.ico">
    <title>Study Online-${model.courseBase.name}</title>

    <link rel="stylesheet" href="/static/plugins/normalize-css/normalize.css" />
    <link rel="stylesheet" href="/static/plugins/bootstrap/dist/css/bootstrap.css" />
    <link rel="stylesheet" href="/static/css/page-learing-article.css" />
</head>

<body data-spy="scroll" data-target="#articleNavbar" data-offset="150">
<!-- 页面头部 -->
<!--#include virtual="/include/header.html"-->
<!--页面头部结束sss-->
<div id="learningArea">
<div class="article-banner">
    <div class="banner-bg"></div>
    <div class="banner-info">
        <div class="banner-left">
            <p>${model.courseBase.mtName}<span>\ ${model.courseBase.stName}</span></p>
            <p class="tit">${model.courseBase.name}</p>
            <p class="pic">
                <#if model.courseBase.charge=='201000'>
                    <span class="new-pic">Free</span>
                <#else>
                    <span class="new-pic">Special Price$ $ ${model.courseBase.price!''}</span>
                    <span class="old-pic">Original Price$ ${model.courseBase.originalPrice!''}</span>
                </#if>
            </p>
            <p class="info">
                <a href="#" @click.prevent="startLearning()">Learn now</a>
                <span><em>Difficulty Level</em>
                <#if model.courseBase.grade=='204001'>
                    Junior
                 <#elseif model.courseBase.grade=='204002'>
                     Intermediate
                <#elseif model.courseBase.grade=='204003'>
                    Senior
                </#if>
                </span>
                <span><em>Course duration</em> 2 hours</span>
                <span><em>Score</em>4.7 Points</span>
                <span><em>Teaching Mode</em>
                 <#if model.courseBase.teachmode=='200002'>
                     Recording
                 <#elseif model.courseBase.teachmode=='200003'>
                     Live
                 </#if>
                </span>
            </p>
        </div>
        <div class="banner-rit">
            <p>
                <a href="http://www.studyonline/course/preview/learning.html?id=${model.courseBase.id}" target="_blank">
                    <#if model.courseBase.pic??>
                        <img src="http://file.studyonline${model.courseBase.pic}" alt="" width="270" height="156">
                    <#else>
                        <img src="/static/img/widget-video.png" alt="" width="270" height="156">
                    </#if>

                </a>
            </p>
            <p class="vid-act"><span> <i class="i-heart"></i>Collect 23 </span> <span>Share <i class="i-weixin"></i><i class="i-qq"></i></span></p>
        </div>
    </div>
</div>
 <div class="article-cont">
    <div class="tit-list">
        <a href="javascript:;" id="articleClass" class="active">Course Introduction</a>
        <a href="javascript:;" id="articleItem">Table of contents</a>
        <a href="javascript:;" id="artcleAsk">Ask</a>
        <a href="javascript:;" id="artcleNot">Notes</a>
        <a href="javascript:;" id="artcleCod">Discussion</a>
    </div>
     <div class="article-box">
        <div class="articleClass" style="display: block">
            <!--<div class="rit-title">评价</div>-->
            <div class="article-cont">
                <div class="article-left-box">
                    <div class="content">

                        <div class="content-com suit">
                            <div class="title"><span>For people</span></div>
                            <div class="cont cktop">
                                <div >
                                    <p>${model.courseBase.users!""}</p>
                                </div>
                                <!--<span class="on-off">更多 <i class="i-chevron-bot"></i></span>-->
                            </div>
                        </div>
                        <div class="content-com course">
                            <div class="title"><span>Course Production</span></div>
                            <div class="cont">
                                <div class="img-box"><img src="/static/img/widget-myImg.jpg" alt=""></div>
                                <div class="info-box">
                                    <p class="name">Teacher：<em> Mark</em></p>
                                    <p class="info">The lectures are logically rigorous and well organized, focusing on students' ability to solve problems independently.</p>
                                </div>
                            </div>

                        </div>
                        <div class="content-com about">
                            <div class="title"><span>Course Introduction</span></div>
                            <div class="cont cktop">
                                <div >
                                    <p>${model.courseBase.description!""}</p>
                                </div>
                            </div>
                        </div>
                        <div class="content-com prob">
                            <div class="title"><span>Question</span></div>
                            <div class="cont">
                                <ul>
                                    <li class="item"><span class="on-off"><i class="i-chevron-bot"></i> When will I be able to access course videos and assignments?</span>
                                        <div class="drop-down">
                                            <p>You can watch the learning videos immediately after paying</p>
                                        </div>
                                    </li>
                                    <li class="item"><span class="on-off"><i class="i-chevron-bot"></i> When will I be able to access course videos and assignments?</span>
                                        <div class="drop-down">
                                            <p>You can watch the learning videos immediately after paying</p>
                                        </div>
                                    </li>
                                    <li class="item"><span class="on-off"><i class="i-chevron-bot"></i> When will I be able to access course videos and assignments?</span>
                                        <div class="drop-down">
                                            <p>You can watch the learning videos immediately after paying</p>
                                        </div>
                                    </li>
                                    <li class="item"><span class="on-off"><i class="i-chevron-bot"></i> When will I be able to access course videos and assignments?</span>
                                        <div class="drop-down">
                                            <p>You can watch the learning videos immediately after paying</p>
                                        </div>
                                    </li>
                                    <li class="item"><span class="on-off"><i class="i-chevron-bot"></i> When will I be able to access course videos and assignments?</span>
                                        <div class="drop-down">
                                            <p>You can watch the learning videos immediately after paying</p>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <!--侧边栏-->
                <!--#include virtual="/include/course_detail_side.html"-->
                <!--侧边栏-->

            </div>
        </div>
        <div class="articleItem" style="display: none">
            <div class="article-cont-catalog">
                <div class="article-left-box">
                    <div class="content">
                        <#list model.teachplans as firstNode>
                            <div class="item">
                                <div class="title act"><i class="i-chevron-top"></i>${firstNode.pname}<span class="time">hours</span></div>
                                <div class="drop-down" style="height: 260px;">
                                    <ul class="list-box">
                                        <#list firstNode.teachPlanTreeNodes as secondNode>
                                            <li><a href="http://www.studyonline.cn/course/preview/learning.html?id=${model.courseBase.id}&chapter=${secondNode.teachplanMedia.teachplanId!''}" target="_blank">${secondNode.pname}</a></li>
                                        </#list>
                                    </ul>
                                </div>
                            </div>
                        </#list>

                    </div>
                </div>
                <!--侧边栏-->
                <!--#include virtual="/include/course_detail_side.html"-->
                <!--侧边栏-->
            </div>
        </div>
    </div>
</div>
</div>
<script>var courseId = "${model.courseBase.id}";var courseCharge = "${model.courseBase.charge}"</script>
</body>
