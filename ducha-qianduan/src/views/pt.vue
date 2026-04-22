<template>
  <div class="header">
    <!-- 大盒子 -->
    <div class="box">
      <!-- 头部区域 -->
      <div class="tou">
        <div class="yong_hu">
          <!-- <span style="color: ">单位 :</span> -->
          <span class="span" style="padding-left: 4px">{{ danWie }}</span>
          <span style="color: #00f6ff">用户名 :</span>
          <span style="padding-left: 4px; color: #00f6ff">{{ yongHu }}</span>
        </div>
        <span
          class="iconfont icon-logout"
          @click="logout"
          style="
            font-size: 24px;
            cursor: pointer;
            position: absolute;
            right: 16px;
            top: 8px;
          "
        ></span>
        <span style="color: #00f6ff; font-size: 1vw" class="tiem">
          {{ times }}
        </span>
        <!-- <img class="liuxian" src="../assets/images/流线.gif" alt />
        <img class="img-left" src="../assets/images/闪烁GIF-慢.gif" alt />-->
      </div>
      <!-- 主体区域 -->
      <div class="centen">
        <!-- 左面盒子 -->
        <div class="boxx">
          <ul
            class="ul"
            v-if="
              str.includes('admin') ||
              str.includes('基层登记') ||
              str.includes('集中文印') ||
              str.includes('工作安排') ||
              str.includes('规章制度') ||
              str.includes('态势分析') ||
              str.includes('通知公告')
            "
          >
            <li v-show="str.includes('基层登记') || str.includes('admin')">
              <div class="demo-box">
                <div class="demo active" v-on:click="doThis">
                  <img src="../images/dj.png" alt />
                  <span class="tanhao" v-show="yuStatus"></span>
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">基 层 登 记</a>
              </div>
            </li>
            <li v-show="str.includes('集中文印') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  id="a1"
                  v-on:click="wenyinfun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/dy.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">集 中 文 印</a>
              </div>
            </li>
            <li v-show="str.includes('工作安排') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="gongzuofun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/gz.png" alt />
                  <span class="tanhao" v-show="yuStatus1"></span>
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">工 作 安 排</a>
              </div>
            </li>
            <li v-show="str.includes('规章制度') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="guizhangfun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/zd.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">规 章 制 度</a>
              </div>
            </li>
            <li v-show="str.includes('态势分析') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="taishifun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/fx.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">态 势 分 析</a>
              </div>
            </li>
            <li v-show="str.includes('通知公告') || str.includes('admin')">
              <div class="demo-box">
                <div
                  id="q1"
                  class="demo active"
                  v-on:click="tongzhifun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../assets/images/通知公告1.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">通 知 公 告</a>
              </div>
            </li>
            <img
              src="../assets/images/椭圆 513 (2).png"
              class="li-img"
              alt
              v-if="
                str.includes('admin') ||
                str.includes('基层登记') ||
                str.includes('集中文印') ||
                str.includes('工作安排') ||
                str.includes('规章制度') ||
                str.includes('态势分析') ||
                str.includes('通知公告')
              "
            />
          </ul>
          <!-- <hr /> -->
          <ul
            class="ul"
            v-if="
              str.includes('admin') ||
              str.includes('战备值班') ||
              str.includes('检查督导') ||
              str.includes('请假销假') ||
              str.includes('安防监控') ||
              str.includes('车行闸道') ||
              str.includes('人行闸道') ||
              str.includes('门禁管理')
            "
          >
            <li v-if="str.includes('战备值班') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="zhanbeifun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/zb.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">战备值班</a>
              </div>
            </li>
            <li v-if="str.includes('检查督导') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="jianchafun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/jc.png" alt />
                  <span class="tanhao" v-show="yuStatus2"></span>
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">检查督导</a>
              </div>
            </li>
            <li v-if="str.includes('请假销假') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="renyuanfun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/ry.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">请假销假</a>
              </div>
            </li>
            <li v-if="str.includes('安防监控') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="anfangfun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/af.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a"> 安防监控</a>
              </div>
            </li>
            <li v-if="str.includes('车行闸道') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="chexingfun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/ag.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">车行闸道</a>
              </div>
            </li>
            <li v-if="str.includes('人行闸道') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="mengangfun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/ag.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">人行闸道</a>
              </div>
            </li>
            <li v-if="str.includes('门禁管理') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  id="a2"
                  data-url="/diary?ref=addtabs"
                  v-on:click="mengJinFun"
                >
                  <img src="../images/mj.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">门禁管理</a>
              </div>
            </li>
            <img
              src="../assets/images/椭圆 513 (2).png"
              class="li-img"
              alt
              v-if="
                str.includes('admin') ||
                str.includes('战备值班') ||
                str.includes('检查督导') ||
                str.includes('请假销假') ||
                str.includes('安防监控') ||
                str.includes('车行闸道') ||
                str.includes('人行闸道') ||
                str.includes('门禁管理')
              "
            />
          </ul>
          <!-- <hr /> -->
          <ul
            class="ul"
            v-if="
              str.includes('admin') ||
              str.includes('手机管理') ||
              str.includes('安全风险预警') ||
              str.includes('思想政治') ||
              str.includes('人力资源')
              // || str.includes('门岗demo')
            "
          >
            <li v-if="str.includes('手机管理') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="zhinengfun"
                  data-url="/diary?ref=addtabs"
                >
                  <img
                    src="../assets/images/智能手机柜.png"
                    alt
                    class="phone"
                  />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">手机管理</a>
              </div>
            </li>
            <li v-if="str.includes('安全风险预警') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="safetyfun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/sx.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">安全风险预警</a>
              </div>
            </li>
            <li v-if="str.includes('思想政治') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="sixiangfun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/sx.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">思想政治教育</a>
              </div>
            </li>
            <li v-if="str.includes('人力资源') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="renlifun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/rl.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">人力资源</a>
              </div>
            </li>
            <li v-if="str.includes('admin') || str.includes('系统工具')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="gongju"
                  data-url="/diary?ref=addtabs"
                  id="xitong"
                >
                  <img src="../images/xtong.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">系统工具</a>
              </div>
            </li>
            <!--            <li v-if="str.includes('门岗demo') || str.includes('admin')">-->
            <!--              <div class="demo-box">-->
            <!--                <div-->
            <!--                  class="demo active"-->
            <!--                  v-on:click="mengangdemofun"-->
            <!--                  data-url="/diary?ref=addtabs"-->
            <!--                >-->
            <!--                  <img src="../images/sx.png" alt />-->
            <!--                </div>-->
            <!--              </div>-->
            <!--              <div class="demo-a">-->
            <!--                <a href="#" class="a">门岗demo</a>-->
            <!--              </div>-->
            <!--            </li>-->
            <img
              src="../assets/images/椭圆 513 (2).png"
              class="li-img"
              alt
              v-if="
                str.includes('admin') ||
                str.includes('手机管理') ||
                str.includes('安全风险预警') ||
                str.includes('思想政治') ||
                str.includes('人力资源')
                // ||str.includes('门岗demo')
              "
            />
          </ul>
          <!-- <hr /> -->
          <ul class="ul">
            <li v-if="str.includes('军车管理') || str.includes('admin')">
              <div class="demo-box">
                <div class="demo active" id="a3" v-on:click="junchefun">
                  <img src="../images/cart.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">军车管理</a>
              </div>
            </li>
            <li v-if="str.includes('私家车管理') || str.includes('admin')">
              <div class="demo-box">
                <div class="demo active" id="a3" v-on:click="sijiachefun">
                  <img src="../images/cart.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">私家车管理</a>
              </div>
            </li>
            <li v-if="str.includes('营房管理') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="yingfangfun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/git.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">营房管理</a>
              </div>
            </li>
            <li v-if="str.includes('库房管理') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="yingjufun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/gl.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">库房管理</a>
              </div>
            </li>
            <li v-if="str.includes('医疗卫生') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="yiliaofun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/yl.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">医疗卫生管理</a>
              </div>
            </li>
            <li v-if="str.includes('涉密载体外借') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="baomifun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../assets/images/保密协议.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">涉密载体外借</a>
              </div>
            </li>
            <li v-if="str.includes('系统管理') || str.includes('admin')">
              <div class="demo-box">
                <div
                  class="demo active"
                  v-on:click="xitongfun"
                  data-url="/diary?ref=addtabs"
                >
                  <img src="../images/xt1.png" alt />
                </div>
              </div>
              <div class="demo-a">
                <a href="#" class="a">系统管理</a>
              </div>
            </li>
          </ul>
        </div>
        <!-- 右面盒子 -->
        <div class="rigin">
          <div class="num1">
            <div class="num2">
              <img src="../images/db.png" alt />
              <span style="font-family: siyuanbold; font-size: 15.5px"
                >待办事项</span
              >
            </div>
            <ul class="num1-ul">
              <div class="num2-ul-title">
                <div class="num2-ul-title-l">任务名称</div>
                <div class="num2-ul-title-c">优先级</div>
                <div class="num2-ul-title-r">执行人</div>
              </div>
              <li
                class="num2-ul-li"
                v-for="item in worklist"
                :key="item.id"
                @click="dbsxfun"
              >
                <div class="num2-ul-li-l">{{ item.workName }}</div>
                <div class="num2-ul-li-c" v-if="item.workPriority == 0">
                  紧急
                </div>
                <div class="num2-ul-li-c" v-if="item.workPriority == 1">高</div>
                <div class="num2-ul-li-c" v-if="item.workPriority == 2">中</div>
                <div class="num2-ul-li-c" v-if="item.workPriority == 3">低</div>
                <div class="num2-ul-li-r">{{ item.executorPeople }}</div>
              </li>
            </ul>
          </div>
          <div class="num1">
            <div class="num2">
              <img src="../assets/images/通知公告.png" alt />
              <span style="font-family: siyuanbold; font-size: 15.5px"
                >通知公告</span
              >
            </div>
            <div class="addspan" @click="tiaddfun">查看更多</div>
            <div class="num1-box">
              <div
                class="num1-box-div"
                v-for="item in noticeList"
                :key="item.noticeId"
                @click="tzggfun(item)"
              >
                <div class="num1-box-l">
                  <span
                    style="
                      color: #bcbcbc;
                      font-size: 14px;
                      font-weight: 400;
                      margin-right: 10px;
                    "
                    >[ {{ item.createTime }} ]</span
                  >
                  {{ item.noticeTitle }}
                </div>
                <div class="num1-box-r">
                  <img
                    src="../assets/images/通知.png"
                    v-if="item.start == 1"
                    alt
                  />
                  <img
                    src="../assets/images/通知 拷贝 3.png"
                    v-if="item.start == 0"
                    alt
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="tkbox2" v-if="open2">
      <div class="tkbox-title">
        <div class="tkbox-title-left">
          <img src="../assets/images/八一.png" alt />
          <span>重要消息</span>
        </div>
        <div class="tkbox-title-right">
          <img src="../assets/images/关闭.png" alt @click="showfun2" />
        </div>
      </div>
      <div class="tkbox-main">
        <p>
          <img src="../assets/images/警示.png" alt />
          <span>{{ title2 }}</span>
        </p>
      </div>
      <div class="tkbox-footer">
        <button @click="quchulifun2">去处理</button>
      </div>
    </div>
    <div class="tkbox" v-if="open">
      <div class="tkbox-title">
        <div class="tkbox-title-left">
          <img src="../assets/images/八一.png" alt />
          <span>重要消息</span>
        </div>
        <div class="tkbox-title-right">
          <img src="../assets/images/关闭.png" alt @click="showfun" />
        </div>
      </div>
      <div class="tkbox-main">
        <p>
          <img src="../assets/images/警示.png" alt />
          <span>{{ title }}</span>
        </p>
      </div>
      <div class="tkbox-footer">
        <button @click="quchulifun">去处理</button>
      </div>
    </div>
    <div class="tkbox3" v-if="open3">
      <div class="tkbox-title">
        <div class="tkbox-title-left">
          <img src="../assets/images/八一.png" alt />
          <span>重要消息</span>
        </div>
        <div class="tkbox-title-right">
          <img src="../assets/images/关闭.png" alt @click="showfun3" />
        </div>
      </div>
      <div class="tkbox-main">
        <p>
          <img src="../assets/images/警示.png" alt />
          <span>{{ title3 }}</span>
        </p>
      </div>
      <div class="tkbox-footer">
        <button @click="quchulifun3">去处理</button>
      </div>
    </div>
    <div class="tkbox4" v-if="open4">
      <div class="tkbox-title">
        <div class="tkbox-title-left">
          <img src="../assets/images/八一.png" alt />
          <span>重要消息</span>
        </div>
        <div class="tkbox-title-right">
          <img src="../assets/images/关闭.png" alt @click="showfun4" />
        </div>
      </div>
      <div class="tkbox-main">
        <p>
          <img src="../assets/images/警示.png" alt />
          <span>{{ title4 }}</span>
        </p>
      </div>
      <div class="tkbox-footer">
        <button @click="quchulifun4">去处理</button>
      </div>
    </div>
    <!-- 提示打印 -->
    <div class="tkbox4" v-if="open6">
      <div class="tkbox-title">
        <div class="tkbox-title-left">
          <img src="../assets/images/八一.png" alt />
          <span>重要消息</span>
        </div>
        <div class="tkbox-title-right">
          <img src="../assets/images/关闭.png" alt @click="showfun5" />
        </div>
      </div>
      <div class="tkbox-main">
        <p>
          <img src="../assets/images/警示.png" alt />
          <span>{{ opentitel }}</span>
        </p>
      </div>
      <div class="tkbox-footer">
        <button @click="quchulifun5">去处理</button>
      </div>
    </div>
    <div class="tkbox4" v-if="open7">
      <div class="tkbox-title">
        <div class="tkbox-title-left">
          <img src="../assets/images/八一.png" alt />
          <span>重要消息</span>
        </div>
        <div class="tkbox-title-right">
          <img src="../assets/images/关闭.png" alt @click="showfun7" />
        </div>
      </div>
      <div class="tkbox-main">
        <p>
          <img src="../assets/images/警示.png" alt />
          <span>{{ opentitel7 }}</span>
        </p>
      </div>
      <div class="tkbox-footer">
        <button @click="quchulifun7">去处理</button>
      </div>
    </div>
    <!-- 添加或修改集中文印记录对话框 -->
    <!-- <el-dialog :title="title" style="margin-top: 200px;" :visible.sync="open5" width="565px" append-to-body>
      <el-form ref="form" :model="form" :inline="true" :rules="rules" label-width="120px">
        <el-form-item label="通知公告标题" prop>
          <el-input value placeholder="请输入通知公告标题" />
        </el-form-item>
        <el-form-item label="通知公告内容">
           <el-input value type="textarea" placeholder="请输入通知公告内容" />
          <el-input value type="textarea" placeholder="请输入通知公告内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" style="width:55px;height:25px;" @click="submitForm">确 定</el-button>
        <button @click="cancel" style="width:55px;height:25px;margin-left:10px;" >取 消</button>
      </div>
    </el-dialog>-->
    <el-dialog
      title="查看通知公告"
      :visible.sync="open5"
      width="800px"
      append-to-body
    >
      <div class="jiben">基本信息</div>
      <el-form ref="form" :model="form" :rules="rules" :inline="true">
        <el-row>
          <el-col :span="12">
            <p
              style="
                width: 100%;
                height: 30px;
                line-height: 30px;
                color: #fff;
                fontsize: 16px;
              "
              v-if="!ggflg"
            >
              公告标题:
              <span style="margin-left: 10px; fontsize: 14px">
                {{ form.noticeTitle }}
              </span>
            </p>
            <el-form-item label="公告标题" v-if="ggflg" prop="noticeTitle">
              <el-input
                v-model="form.noticeTitle"
                placeholder="请输入公告标题"
              />
            </el-form-item>
            <!-- <el-form-item label="公告标题" prop="noticeTitle">
              <el-input

                v-model="form.noticeTitle"
                placeholder="请输入公告标题"
                style="width: 300px"
              />
            </el-form-item>-->
          </el-col>
          <el-col :span="12" v-if="ggflg">
            <el-form-item label="公告类型" prop="noticeType">
              <el-select v-model="form.noticeType" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.sys_notice_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col
            :span="24"
            style="margin-top: 15px; margin-bottom: 10px"
            v-if="ggflg"
          >
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_notice_status"
                  :key="dict.value"
                  :label="dict.value"
                  style="margin-right: 20px"
                  >{{ dict.label }}</el-radio
                >
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="margin-top: 10px">
          <el-col :span="12" v-if="!ggflg">
            <el-form-item label="发布人" prop="createBy" label-width="0">
              <el-input
                v-model="form.createBy"
                disabled
                placeholder="请输入发布人"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="!ggflg">
            <el-form-item label="附件" label-width="0px" prop="filename">
              <a
                :href="url + form.filename"
                target="_blank"
                download
                style="
                  display: inline-block;
                  width: 90px;
                  height: 30px;
                  background-color: #409eff;
                  border: none;
                  border-radus: 3px;
                  color: #fff;
                  line-height: 30px;
                  text-align: center;
                "
                >下载附件</a
              >
              <!-- <fileUpload v-model="form.filename" class="m-l-20" /> -->
            </el-form-item>
          </el-col>
        </el-row>
        <div class="jiben">内容</div>
        <el-col :span="24">
          <el-form-item
            label
            prop="remark"
            class="textareas"
            style="width: 100%"
          >
            <el-input
              v-model="form.remark"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <button class="button1" v-if="ggflg" @click="submitForm">确 定</button>
        <button @click="cancel">取 消</button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  printTrigger,
  printTriggerClear,
  printPeople,
  printEachTrigger,
} from "@/api/PrintsManage/manage";
import {
  workEachTrigger,
  workTrigger,
  addNotice,
  listNotice,
  updateNotice,
  getWorkTask,
  getNone,
  noReminder,
  remindPrint,
  syTaskView,
  getInfoD,
} from "@/api/pt";
import { getUserProfile } from "@/api/system/user";
import Cookies from "js-cookie";
import axios from "axios";
import WebSocketService from "@/utils/WebSocketService";

export default {
  dicts: ["sys_notice_status", "sys_notice_type"],
  data() {
    return {
      times: null,
      timer: null,
      open: false,
      open2: false,
      open3: false,
      open4: false,
      timer2: null,
      open5: false,
      ggflg: false,
      title: "",
      title2: "",
      title3: "",
      title4: "",
      url: process.env.VUE_APP_BASE_API,
      form: "",
      rules: {
        noticeTitle: [
          { required: true, message: "公告标题不能为空", trigger: "blur" },
        ],
        noticeType: [
          { required: true, message: "公告类型不能为空", trigger: "change" },
        ],
      },
      noticeList: [],
      worklist: [],
      // 用户信息
      user: {},
      danWie: "",
      yongHu: "",
      str: "",
      // 获取预警列表
      yuJing: [],
      yuStatus: false,
      yuStatus1: false,
      yuStatus2: false,
      // 提示打印
      open6: false,
      open7: false,
      opentitel7: null,
      opentitel: null,
    };
  },
  created() {
    this.getUser();
    this.timer = null;
    this.startTime();
    this.timer2 = null;
    this.startTime2();
    this.xianShang();
    this.getTiXing();
    getNone().then((res) => {
      console.log("getNone", res);
      this.str = res.msg;
    });
    noReminder().then((res) => {
      if (res.data[0] != 0) {
        this.yuStatus = true;
      }
      if (res.data[1] != 0) {
        this.yuStatus1 = true;
      }
      if (res.data[2] != 0) {
        this.yuStatus2 = true;
      }
    });
  },
  mounted() {
    if (this.$route.params.name) {
      this.$modal.msgSuccess(this.$route.params.name);
    }
    this.getlist();
    this.getworklist();
  },
  methods: {
    // 获取提示信息
    getTiXing() {
      remindPrint().then((res) => {
        console.log("getTiXing", res);
        this.opentitel = res.msg;
        if (res.num == 0) {
          this.open6 = false;
        } else {
          var ti = Cookies.get("tixing") || false;
          if (ti) {
            this.open6 = false;
          } else {
            this.open6 = true;
            Cookies.set("tixing", "true");
          }
        }
      });
    },
    // 退出
    async logout() {
      try {
        await this.$confirm("确定注销并退出系统吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        });

        // 调用 Vuex 退出登录
        await this.$store.dispatch("LogOut");

        // 清理本地数据
        Cookies.remove("tixing");

        // 断开 WebSocket（如果存在）
        if (this.$store.getters.webSocketState === "open") {
          WebSocketService.close();
        }

        // 跳转登录页
        location.href = "/index";
      } catch (err) {
        // 用户取消操作，不处理
      }
    },
    // 手机柜线S地址console
    xianShang() {
      // axios
      //   // 线上地址
      //   .post("http://172.4.5.232:12092/wl/api/getAccessToken", {
      //     password: "123456",
      //     username: "admin"
      //   })
      //   .then(res => {
      //     Cookies.set("accessToken", res.data.accessToken);
      //   });
      Cookies.set("accessToken", "res.data.accessToken");
    },
    // 获取登录信息
    getUser() {
      getUserProfile().then((response) => {
        console.log("getUserProfile", response);
        this.user = response.data;
        this.danWie = response.data.dept.deptName;
        this.yongHu = response.data.nickName;
      });
    },
    getlist() {
      listNotice().then((res) => {
        this.noticeList = res.data;

        this.noticeList.forEach((item) => {
          // var newDate=/\d{4}-\d{1,2}-\d{1,2}/g.exec(item.createTime)
          var a = item.createTime.substring(0, 10).split("-");
          return (item.createTime = a[0] + "-" + a[1] + "-" + a[2]);
        });
      });
    },
    // 获取代办事项
    getworklist() {
      syTaskView().then((res) => {
        this.worklist = res.rows;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        noticeId: undefined,
        noticeTitle: undefined,
        noticeType: undefined,
        noticeContent: undefined,
        status: "0",
      };
      this.resetForm("form");
    },
    startTime() {
      this.timer = setInterval(() => {
        var today = new Date();
        var y = today.getFullYear();
        var M = today.getMonth() + 1;
        var d = today.getDate();
        var h = today.getHours();
        var m = today.getMinutes();
        var s = today.getSeconds();
        var w = today.getDay();
        if (M < 10) {
          M = "0" + M;
        }
        if (d < 10) {
          d = "0" + d;
        }
        if (h < 10) {
          h = "0" + h;
        }
        if (m < 10) {
          m = "0" + m;
        }
        if (s < 10) {
          s = "0" + s;
        }
        this.times = y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;
      }, 500); /* 每500毫秒执行一次，实现动态显示时间效果 */
    },
    doThis() {
      this.$router
        .push({ path: "/index", query: { name: "/grassrootsregistration" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    zhanbeifun() {
      this.$router
        .push({ path: "/index", query: { name: "/combatduty" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    safetyfun() {
      this.$router
        .push({ path: "/index", query: { name: "/safety" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    mengangdemofun() {
      this.$router
        .push({ path: "/index", query: { name: "/demo" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    renlifun() {
      this.$router
        .push({ path: "/index", query: { name: "/peopleManage" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    renyuanfun() {
      this.$router
        .push({ path: "/index", query: { name: "/peopleChuRu" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    yingfangfun() {
      this.$router
        .push({ path: "/index", query: { name: "/barracksManagement" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    junchefun() {
      this.$router
        .push({ path: "/index", query: { name: "/militaryvehicleManagement" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    sijiachefun() {
      this.$router
        .push({ path: "/index", query: { name: "/privatecarManagement" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    gongzuofun() {
      this.$router
        .push({ path: "/index", query: { name: "/workingArrangements" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    guizhangfun() {
      this.$router
        .push({ path: "/index", query: { name: "/regulations" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    yiliaofun() {
      this.$router
        .push({ path: "/index", query: { name: "/medicalhealth" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    jianchafun() {
      this.$router
        .push({ path: "/index", query: { name: "/bdglregular" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    anfangfun() {
      this.$router
        .push({ path: "/index", query: { name: "/monitoring" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    chexingfun() {
      this.$router
        .push({ path: "/index", query: { name: "/doorcarManage" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    mengangfun() {
      this.$router
        .push({ path: "/index", query: { name: "/doormanManage" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    mengJinFun() {
      this.$router
        .push({ path: "/index", query: { name: "/access" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    yingjufun() {
      this.$router
        .push({ path: "/index", query: { name: "/materialManagement" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    xitongfun() {
      this.$router
        .push({ path: "/index", query: { name: "/system" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    gongju() {
      this.$router
        .push({ path: "/index", query: { name: "/tool" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    wenyinfun() {
      this.$router
        .push({ path: "/index", query: { name: "/print" } })
        .catch(() => {});
      Cookies.set("shuaxin", "false");
    },
    taishifun() {
      this.$router.push({ path: "/taishi" }).catch(() => {});
      Cookies.set("shuaxin", "false");
      // window.open(`/taishi`,'_self');
    },
    baomifun() {
      this.$router
        .push({ path: "/index", query: { name: "/bdglSecret" } })
        .catch(() => {});
    },
    sixiangfun() {
      this.$router
        .push({ path: "/index", query: { name: "/thought" } })
        .catch(() => {});
    },
    zhinengfun() {
      this.$router
        .push({ path: "/index", query: { name: "/phone" } })
        .catch(() => {});
    },
    showfun() {
      this.open = false;
      printTriggerClear().then((res) => {});
    },
    showfun2() {
      this.open2 = false;
      printTriggerClear().then((res) => {});
    },
    showfun3() {
      this.open3 = false;
      printTriggerClear().then((res) => {});
    },
    showfun4() {
      this.open4 = false;
      printTriggerClear().then((res) => {});
    },
    showfun5() {
      this.open6 = false;
      printTriggerClear().then((res) => {});
    },
    showfun7() {
      this.open7 = false;
      printTriggerClear().then((res) => {});
    },
    quchulifun() {
      printTriggerClear().then((res) => {
        if (res.code == 200) {
          this.open = true;
          window.open(`/print/manage`, "_self");
        }
      });
    },
    quchulifun2() {
      printTriggerClear().then((res) => {
        if (res.code == 200) {
          this.open = false;
          window.open(`/print/manage`, "_self");
        }
      });
    },
    quchulifun3() {
      printTriggerClear().then((res) => {
        if (res.code == 200) {
          this.open3 = false;
          window.open(`/#/workingArrangements/workTask`, "_self");
        }
      });
    },
    quchulifun4() {
      printTriggerClear().then((res) => {
        if (res.code == 200) {
          this.open4 = false;
          window.open(`/#/workingArrangements/planTask`, "_self");
        }
      });
    },
    quchulifun5() {
      Cookies.set("tixing", "false");
      printTriggerClear().then((res) => {
        if (res.code == 200) {
          window.open(`/#/print/printlog`, "_self");
        }
      });
    },
    quchulifun7() {
      printTriggerClear().then((res) => {
        if (res.code == 200) {
          window.open(`/#/barracksManagement/repair`, "_self");
        }
      });
    },
    startTime2() {
      this.timer = setInterval(() => {
        // printTrigger().then((res) => {
        //   if (res.code == 200) {
        //     if (res.print == "print") {
        //       if (res.numb != 0) {
        //         this.title2 = "您有打印文件需要处理，请尽快处理";
        //         this.open2 = true;
        //       }
        //     }
        //   }
        // });
        printEachTrigger().then((res) => {
          if (res.code == 200) {
            if (res.print == "printUserInfo") {
              var sendId = Cookies.get("userId");
              if (res.printUserInfo == sendId) {
                if (res.numb != 0) {
                  this.title = res.msg;
                  this.open = true;
                }
              }
            }
          }
        });
        workEachTrigger().then((res) => {
          var sendId = Cookies.get("userId");
          if (res.code == 200) {
            if (res.workEach != 0) {
              if (res.workUserid == sendId) {
                this.title3 = res.msg;
                this.open3 = true;
              }
            }
          }
        });
        workTrigger().then((res) => {
          // var sendId = Cookies.get("userId");
          if (res.code == 200) {
            if (res.workRequest != 0) {
              this.title4 = res.msg;
              this.open4 = true;
            }
          }
        });
        getInfoD().then((res) => {
          if (res.code == 200) {
            if (res.data != 0) {
              this.opentitel7 = res.data;
              this.open7 = true;
            }
          }
        });
      }, 10000); /* 每500毫秒执行一次，实现动态显示时间效果 */
    },
    tongzhifun() {
      // this.$router.push({ path: "/system/notice" }).catch(() => {});
      this.$router
        .push({ path: "/index", query: { name: "/notice" } })
        .catch(() => {});
    },
    submitForm() {
      // addNotice(this.form).then((response) => {
      //   this.$modal.msgSuccess("新增成功");
      //   this.open5 = false;
      //   this.ggflg = false;
      //   this.getlist();
      // });
    },
    cancel() {
      this.open5 = false;
      this.ggflg = false;
      this.getlist();
    },
    tiaddfun() {
      this.$router
        .push({ path: "/index", query: { name: "/notice" } })
        .catch(() => {});
      // this.$router.push({ path: "/system/notice" }).catch(() => {});
    },
    tzggfun(item) {
      // item.start = 1;
      this.reset();
      this.form = item;
      updateNotice({
        noticeId: item.noticeId,
        start: 1,
        noticeTitle: item.noticeTitle,
      }).then((res) => {
        if (res.code == 200) {
          this.getlist();
        }
      });
      this.open5 = true;
      this.ggflg = false;
    },
    dbsxfun() {
      this.$router
        .push({ path: "/workingArrangements/workCalendar" })
        .catch(() => {});
    },
  },
  destroyed() {
    clearInterval(this.timer); // 清除定时器
    this.timer = null;
    clearInterval(this.timer2); // 清除定时器
    this.timer2 = null;
  },
};
</script>

<style scoped>
.el-dialog {
  margin-top: 500px;
}
* {
  margin: 0;
  padding: 0;
}
body,
html {
  height: 100%;
  width: 100%;
}
li {
  list-style: none;
}
.header {
  height: 100%;
  width: 100%;
  overflow: auto;
  background: url("~@/images/背景1.png") no-repeat center;
  background-size: 100% 100%;
  position: relative;
}
.tou {
  color: #fff;
  position: relative;
  height: 120px;
  background: url("~@/assets/images/首页顶部.gif") no-repeat center;
  background-size: cover;
}
.tou .yong_hu {
  position: absolute;
  right: 24px;
  top: 33px;
  min-width: 320px;
  font-size: 16px;
  box-sizing: border-box;
}
.tou .tiem {
  position: absolute;
  /* right: 24px; */
  left: 124px;
  top: 33px;
  min-width: 320px;
  font-size: 15px;
  box-sizing: border-box;
}
.tou .span {
  margin-right: 20px;
}
.liuxian {
  position: absolute;
  top: -7px;
}
.in1 {
  padding: 0 84px;
}
.demo {
  color: #fff;
  width: 107px;
  height: 95%;
  background: url("~@/images/tx.png") no-repeat;
  text-align: center;
  margin-top: 10%;
  position: relative;
  /* margin-bottom: 10px; */
  /* margin: 0 80px; */
}
.demo img {
  margin-top: 35px;
}
#q1 img {
  margin-top: 42px;
}
.demo-box {
  width: 100%;
  height: 70%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.demo-a {
  width: 100%;
  height: 30%;
  font-size: 1rem;
  color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
}
.ul {
  margin-bottom: 20px;
  height: 23%;
  display: flex;
  flex-flow: wrap;
}
.ul li {
  /* margin: 0 48px; */
  cursor: pointer;
  width: 14.1%;
  height: 100%;
  display: inline-block;
}
.li-img {
  width: 100%;
  /* height: 2px;
  background-color: #06668a;
  border: none;
  margin-bottom: 20px; */
}
.boxx {
  padding: 0 0 0 80px;
  flex: 2.2;
}

.img-left {
  position: absolute;
  top: -8px;
}

hr {
  height: 2px;
  background-color: #06668a;
  border: none;
  margin-bottom: 20px;
}
.centen {
  display: flex;
  height: 85vh;
}
.num1 {
  color: #fff;
  width: 529px;
  height: 41vh;
  background: url("~@/images/底3.png") no-repeat;
  margin-bottom: 10px;
  padding: 16px 20px 0px 20px;
  background-size: 100% 100%;
}
.num1 img {
  /* width: 20px;
  height: 20px; */
  vertical-align: bottom;
  margin-right: 10px;
}
.rigin {
  flex: 1;
  margin-left: 52px;
}
.table {
  margin-top: 20px;
  width: 492px;
  height: 80%;
  /* height: 182px; */
  /* 去除中间的线 */
  border-collapse: collapse;
  border-spacing: 0;
}
.table tr td {
  height: 28px;
  text-align: center;
}
.table tr:nth-child(even) {
  background-color: rgba(39, 104, 136, 0.2);
}
.table tr:hover {
  background: rgba(0, 246, 255, 0.3);
}
#a2 img {
  margin-top: 40px;
}
#a1 img {
  margin-top: 40px;
}
#a3 img {
  margin-top: 43px;
}
.num1-box {
  width: 100%;
  height: 86%;
  margin-top: 5%;
}
.num1-box .num1-box-div {
  width: 100%;
  height: 36px;
  display: flex;
  flex-direction: row;
  font-size: 14px;
  color: #fff;
  line-height: 36px;
}
.num1-box-div:nth-child(odd) {
  background-color: rgba(39, 104, 136, 0.2);
}
.num1-box-div:hover {
  background: rgba(0, 246, 255, 0.3);
}
.num1-box-l {
  flex: 8;
  height: 100%;
  text-align: left;
  margin-left: 10px;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
.num1-box-r {
  flex: 1;
  height: 100%;
  text-align: right;
  margin-right: 10px;
  margin-top: -4px;
  line-height: 30px;
}
.num1-ul {
  width: 100%;
  height: 86%;
  margin-top: 2%;
  overflow: auto;
}
.num1-ul::-webkit-scrollbar {
  width: 14px;
  /* height: 2px !important; */
  background: #002745;
}

/*定义滑块，内阴影及圆角*/
.num1-ul::-webkit-scrollbar-thumb {
  background: rgba(0, 39, 69, 1);
}
.num1-ul-li {
  width: 100%;
  height: 36px;
  display: flex;
  flex-direction: row;
  font-size: 14px;
  color: #fff;
  line-height: 36px;
}
.num1-ul-li:nth-child(even) {
  background-color: rgba(39, 104, 136, 0.2);
}
.num1-ul-li:hover {
  background: rgba(0, 246, 255, 0.3);
}
.num1-ul-li-l {
  flex: 7;
  height: 100%;
  text-align: center;
}
.num1-ul-li-r {
  flex: 3;
  height: 100%;
  text-align: center;
}
.num2-ul-li {
  width: 100%;
  height: 36px;
  display: flex;
  flex-direction: row;
  font-size: 14px;
  color: #fff;
  line-height: 36px;
}
.num2-ul-li:nth-child(even) {
  background-color: rgba(39, 104, 136, 0.2);
}
.num2-ul-li:hover {
  background: rgba(0, 246, 255, 0.3);
}
.num2-ul-li-l {
  flex: 6;
  height: 100%;
  text-align: left;
  margin-left: 10px;
}
.num2-ul-li-c {
  flex: 3;
  height: 100%;
  text-align: center;
}
.num2-ul-li-r {
  flex: 3;
  height: 100%;
  text-align: center;
}
.num1-ul-title {
  width: 100%;
  height: 45px;
  display: flex;
  flex-direction: row;
  font-size: 16px;
  color: #fff;
  line-height: 45px;
}
.num1-ul-title-l {
  flex: 7;
  height: 100%;
  text-align: center;
}
.num1-ul-title-r {
  flex: 3;
  height: 100%;
  text-align: center;
}
.num2-ul-title {
  width: 100%;
  height: 45px;
  display: flex;
  flex-direction: row;
  font-size: 16px;
  color: #fff;
  line-height: 45px;
}
.num2-ul-title-l {
  flex: 6;
  height: 100%;
  text-align: left;
  margin-left: 10px;
}
.num2-ul-title-c {
  flex: 3;
  height: 100%;
  text-align: center;
}
.num2-ul-title-r {
  flex: 3;
  height: 100%;
  text-align: center;
}
.num1 {
  position: relative;
}
.num1 .addspan:hover {
  color: #1d96ff;
}
.num1 .addspan {
  width: 80px;
  /* background-color: pink; */
  height: 20px;
  display: inline-block;
  /* background-color: #1c86e1; */
  line-height: 25px;
  text-align: center;
  font-size: 14px;
  cursor: pointer;
  color: #dcdcdc;
  background-repeat: no-repeat;
  /* margin-left: 3.5px; */
  background-position: 70px 5px;
  position: absolute;
  right: 1.6%;
  top: 2%;
  font-family: siyuan;
}
::v-deep .el-dialog {
  background-color: #004d86;
}

::v-deep .el-dialog__header {
  border-bottom: 1px solid #718a9d;
}
::v-deep .el-dialog__title {
  color: #fff;
  font: 18px;
}
::v-deep .el-dialog__headerbtn .el-dialog__close {
  color: #fff;
}
.el-form-item ::v-deep .el-input__inner {
  width: 355px;
  height: 36px;
  background: #004d86;
  color: #000000;
  border: 1px solid #fff !important;
}
::v-deep .el-textarea__inner {
  width: 450px;
  height: 104px;
  margin-left: 25px;
}
/* 通知 */
::v-deep .el-tag.el-tag--success {
  background-color: #e7faf0;
  border-color: #d0f5e0;
  color: #13ce66;
}
/* 公告 */
::v-deep .el-tag.el-tag--warning {
  background-color: #fff8e6;
  border-color: #fff1cc;
  color: #ffba00;
}
/* 对话框背景颜色 */
::v-deep .el-dialog {
  background: #004d86 !important;
}
::v-deep .el-textarea__inner {
  width: 760px;
  height: 104px;
  margin: auto !important;
}
::v-deep .el-dialog__header {
  border-bottom: 1px solid #718a9d;
}
::v-deep .el-dialog__title {
  color: #fff;
  font: 18px;
}
::v-deep .el-dialog__headerbtn .el-dialog__close {
  color: #fff;
}
::v-deep .el-form-item__label {
  font: 16px;
  color: #fff;
  width: auto !important;
}
::v-deep .el-input__inner {
  width: 250px !important;
  height: 36px;
  background: transparent;
  color: #fff;
}
/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
}
/* 基本信息背景 */
.jiben {
  width: 760px;
  height: 32px;
  background-image: url("~@/images/小标题底.png");
  margin-bottom: 25px;
  color: #fff;
  padding-left: 16px;
  line-height: 32px;
  margin-top: 10px;
}
/*调整表单间距 */
::v-deep .el-form-item__content {
  width: 200px;
}
.contents {
  padding: 0px 40px !important;
}

/* 单位框背景颜色 */
::v-deep .vue-treeselect__control {
  background: #004d86 !important;
  color: #fff;
}
/* 备注输入框 */
.textareas ::v-deep .el-form-item__content {
  width: 100%;
}
/* 状态 */
.el-radio {
  color: rgb(204, 198, 198);
}
/* 备注输入框 */
.textareas ::v-deep .el-form-item__content {
  width: 100%;
}
/* 富文本 */
::v-deep .ql-snow .ql-fill,
.ql-snow .ql-stroke.ql-fill {
  fill: #fff !important;
}
::v-deep .ql-snow .ql-stroke {
  stroke: #fff !important;
}
::v-deep .ql-snow .ql-picker {
  color: #fff !important;
}
::v-deep .ql-editor ql-blank {
  color: #fff !important;
}
/* 分页按钮 */
::v-deep .el-pagination.is-background .el-pager li {
  background-color: #004d86;
  color: #fff;
}
::v-deep .el-pagination.is-background .btn-next {
  background-color: #004d86;
  color: #fff;
}
::v-deep .el-pagination.is-background .btn-prev,
.el-pagination.is-background .btn-next,
.el-pagination.is-background .el-pager li {
  background: #004d86 !important;
  color: #fff !important;
}
.el-select-dropdown__item {
  left: 10px !important;
}
::v-deep .el-upload__tip {
  display: none;
}
</style>
<style lang="scss" scoped>
.tkbox {
  width: 500px;
  height: 220px;
  background-image: url("~@/assets/images/框.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  position: absolute;
  left: 20%;
  top: 30%;
}

.tkbox2 {
  width: 500px;
  height: 220px;
  background-image: url("~@/assets/images/框.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  position: absolute;
  right: 26%;
  top: 30%;
}

.tkbox3 {
  width: 500px;
  height: 220px;
  background-image: url("~@/assets/images/框.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  position: absolute;
  left: 20%;
  top: 38%;
}

.tkbox4 {
  width: 500px;
  height: 220px;
  background-image: url("~@/assets/images/框.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  position: absolute;
  right: 42%;
  top: 38%;
}

.tkbox-title {
  width: 100%;
  height: 90px;
}

.tkbox-title-left {
  width: 90%;
  display: inline-block;
  line-height: 90px;
  position: relative;
}

.tkbox-title-left img {
  position: absolute;
  left: 35px;
  top: 30px;
}

.tkbox-title-left span {
  color: #fff;
  line-height: 95px;
  margin-left: 75px;
}

.tkbox-title-right {
  width: 10%;
  text-align: right;
  display: inline-block;
}

.tkbox-title-right img {
  text-align: right;
  margin-right: 30px;
}

.tkbox-main {
  width: 100%;
  height: 60px;
}

.tkbox-main p {
  width: 90%;
  height: auto;
  margin: 0 auto;
  color: #fff;
  font-size: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.tkbox-main p span {
  margin-left: 10px;
}

.tkbox-footer {
  width: 100%;
  height: 30px;
  margin-bottom: 30px;
  text-align: center;
}

.fixed-header + .app-main {
  padding-top: 50px;
}

.hasTagsView {
  .app-main {
    /* 84 = navbar + tags-view = 50 + 34 */
    min-height: calc(100vh - 84px);
  }

  .fixed-header + .app-main {
    padding-top: 84px;
  }
}
.tkbox-footer button {
  width: 60px;
  height: 25px;
  text-align: center;
  line-height: 21px;
  background-color: #409eff;
  color: #fff;
  border-radius: 6px;
  border: none;
}
.dialog-footer button {
  width: 60px;
  height: 30px;
  text-align: center;
  line-height: 21px;
  color: #fff;
  border-radius: 3px;
  border: none;
  background-color: #196299;
  margin-top: 20px;
}
.dialog-footer .button1 {
  background-color: #409eff;
  margin-right: 10px;
}
.phone {
  margin-top: 40px !important;
}
#xitong img {
  margin-top: 44px !important;
}
.tanhao {
  position: absolute;
  right: 7%;
  width: 30px;
  height: 30px;
  top: 6.5%;
  background: url(~@/images/无光_00000.png) no-repeat;
  animation: mymove 2s 0.5s infinite linear alternate forwards;
}
@keyframes mymove {
  0% {
    background: url(~@/images/无光_00000.png) no-repeat;
  }
  3% {
    background: url(~@/images/无光_00001.png) no-repeat;
  }
  6% {
    background: url(~@/images/无光_00002.png) no-repeat;
  }
  9% {
    background: url(~@/images/无光_00003.png) no-repeat;
  }
  12% {
    background: url(~@/images/无光_00004.png) no-repeat;
  }
  15% {
    background: url(~@/images/无光_00005.png) no-repeat;
  }
  18% {
    background: url(~@/images/无光_00006.png) no-repeat;
  }
  21% {
    background: url(~@/images/无光_00007.png) no-repeat;
  }
  24% {
    background: url(~@/images/无光_00008.png) no-repeat;
  }
  27% {
    background: url(~@/images/无光_00009.png) no-repeat;
  }
  30% {
    background: url(~@/images/无光_00010.png) no-repeat;
  }
  33% {
    background: url(~@/images/无光_00011.png) no-repeat;
  }
  36% {
    background: url(~@/images/无光_00012.png) no-repeat;
  }
  39% {
    background: url(~@/images/无光_00013.png) no-repeat;
  }
  42% {
    background: url(~@/images/无光_00014.png) no-repeat;
  }
  45% {
    background: url(~@/images/无光_00015.png) no-repeat;
  }
  48% {
    background: url(~@/images/无光_00016.png) no-repeat;
  }
  51% {
    background: url(~@/images/无光_00017.png) no-repeat;
  }
  54% {
    background: url(~@/images/无光_00018.png) no-repeat;
  }
  57% {
    background: url(~@/images/无光_00019.png) no-repeat;
  }
  60% {
    background: url(~@/images/无光_00020.png) no-repeat;
  }
  63% {
    background: url(~@/images/无光_00021.png) no-repeat;
  }
  66% {
    background: url(~@/images/无光_00022.png) no-repeat;
  }
  69% {
    background: url(~@/images/无光_00023.png) no-repeat;
  }
  72% {
    background: url(~@/images/无光_00024.png) no-repeat;
  }
  75% {
    background: url(~@/images/无光_00025.png) no-repeat;
  }
  78% {
    background: url(~@/images/无光_00026.png) no-repeat;
  }
  81% {
    background: url(~@/images/无光_00027.png) no-repeat;
  }
  84% {
    background: url(~@/images/无光_00028.png) no-repeat;
  }
  87% {
    background: url(~@/images/无光_00029.png) no-repeat;
  }
  90% {
    background: url(~@/images/无光_00030.png) no-repeat;
  }
  92% {
    background: url(~@/images/无光_00031.png) no-repeat;
  }
  95% {
    background: url(~@/images/无光_00032.png) no-repeat;
  }
  98% {
    background: url(~@/images/无光_00033.png) no-repeat;
  }
  100% {
    background: url(~@/images/无光_00034.png) no-repeat;
  }
}
</style>
