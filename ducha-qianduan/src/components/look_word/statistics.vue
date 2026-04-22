<template>
  <div>
   

    <div class="word" id="contents">
      <p
        style="
          font-size: 14pt;
          font-weight: 900;
          margin-botttom: 30px;
          color: black;
        "
      >
        《军事训练月统计报表》
      </p>
 
    </div>
  </div>
</template>
<script>
import { lookWord } from "@/utils/look_word.js";
import "@/utils/lodJs.js";
export default {
  name: "bdgleven",
  props: ["message"],
  data() {
    return {
      title: "军事训练月统计报表",
      style:
        ".JR_tr {height: 40px;};.JR_left{width: 120px;font-size: 1.331em; font-family: '楷体_GB2312';};.JR_right{ font-size: 1.331em; font-family: 仿宋_GB2312;};.JR_bottom{border-left:0; font-size: 1.331em; font-family: 仿宋_GB2312; text-align: right}",
    };
  },
  methods: {
    daochu() {
      lookWord(this.title, "#contents", this.style, true);
    },
  },
};
</script>
<style scoped>
tbody {
  color: #fff;
  text-align: center;
}
tr:last-child {
  border-bottom: 1px solid;
}
::v-deep .contents {
  width: 1047px !important;
}
.box1 {
  position: absolute;
  right: 30px;
  top: 80px;
  border: 1px solid rgb(129, 130, 131);
  border-radius: 5px;
}
</style>