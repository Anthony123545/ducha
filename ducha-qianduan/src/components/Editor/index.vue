<template>
  <div>
    <div class="local-quill-editor">
      <quill-editor
        ref="myLQuillEditor"
        v-model="content"
        :options="editorOption"
        class="editor"
        @change="onEditorChange"
      >
      </quill-editor>
    </div>
  </div>
  <!-- @blur="onEditorBlur" @focus="onEditorFocus" @change="onEditorChange"> -->
</template>

<script>
// 引入样式和quillEditor
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";
import { quillEditor } from "vue-quill-editor";

// 工具栏配置项
const toolbarOptions = [
  // 加粗 斜体 下划线 删除线 -----['bold', 'italic', 'underline', 'strike']
  ["bold", "italic", "underline", "strike"],

  // 引用  代码块-----['blockquote', 'code-block']
  ["blockquote"],

  // 1、2 级标题-----[{ header: 1 }, { header: 2 }]
  [{ header: 1 }, { header: 2 }],

  // 有序、无序列表-----[{ list: 'ordered' }, { list: 'bullet' }]

  // 上标/下标-----[{ script: 'sub' }, { script: 'super' }]

  // 缩进-----[{ indent: '-1' }, { indent: '+1' }]
  [{ indent: "-1" }, { indent: "+1" }],

  // 文本方向-----[{'direction': 'rtl'}]
  [{ direction: "rtl" }],

  // 字体大小-----[{ size: ['small', false, 'large', 'huge'] }]
  [{ size: ["small", false, "large", "huge"] }],

  // 标题-----[{ header: [1, 2, 3, 4, 5, 6, false] }]
  [{ header: [1, 2, 3, 4, 5, 6, false] }],

  // 字体颜色、字体背景颜色-----[{ color: [] }, { background: [] }]
  [{ color: [] }, { background: [] }],

  // 字体种类-----[{ font: [] }]

  // 对齐方式-----[{ align: [] }]
  [{ align: [] }],

  // 清除文本格式-----['clean']
  ["clean"],

  // 链接、图片、视频-----['link', 'image', 'video']
  ["image", "video"],
];

export default {
  name: "LocalQuillEditor",
  // 注册 quillEditor
  components: {
    quillEditor,
  },
  props: {
    options: Object,
  },
  data() {
    return {
      content: "",
      editorOption: {
        modules: {
          toolbar: toolbarOptions,
        },
        theme: "snow",
        placeholder: "请输入正文",
        // Some Quill optiosn...
      },
    };
  },
  methods: {
    // 失去焦点事件
    onEditorBlur(e) {
      console.log;
    },
    // // 获得焦点事件
    // onEditorFocus(e) {
    //   console.log('onEditorFocus: ', e)
    // },
    // 内容改变事件
    onEditorChange(e) {
      // console.log('onEditorChange: ', e)
      this.$emit("btn", this.content);
    },
  },
};
</script>

<style scoped lang="scss">
.editor {
  height: 130px;
  width: 827px;
  margin: auto;
  margin-bottom: 50px;
}
.local-quill-editor p {
  color: #fff;
}
</style>