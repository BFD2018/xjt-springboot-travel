<template>
  <div class="app-container">
    <div id="wangeditor">
      <p class="warn-content">
        富文本基于
        <el-link type="primary" href="https://www.kancloud.cn/wangfupeng/wangeditor3/332599" target="_blank">wangEditor</el-link>
      </p>
    </div>

    <el-row type="flex" justify="space-between" style="margin-top: 20px;">
      <el-col push=23>
        <el-button type="success" @click="previewEditorContent">预览</el-button>
      </el-col>
    </el-row>

    <el-dialog title="预览编辑内容" :visible.sync="dialogTableVisible">
      <div id="editor-content" v-html="editorContent"></div>
    </el-dialog>
  </div>
</template>

<script>
  import E from "wangeditor"

export default {
  name: 'MyWangEditor',
  data() {
    return {
      editor:null,
      dialogTableVisible:false,
      editorContent:"",
    }
  },
  methods: {
    initWangEditor(){
      this.editor = new E("#wangeditor")

      this.editor.config.zIndex = 100

      // 设置编辑区域高度为 500px
      this.editor.config.height = 500
      // 注意，先配置 height ，再执行 create()
      this.editor.create()
    },

    previewEditorContent(){
      this.dialogTableVisible = true;
      this.editorContent = this.editor.txt.html();
    },
  },
  mounted() {
    this.$nextTick(() =>{
      this.initWangEditor();
    });
  }
}
</script>

<style lang="less" scoped>
  .app-container{
    width: 100%;
    height: auto;
    text-align: left;
    .toolbar {
      border: 1px solid #ccc;
    }
    .text {
      border: 1px solid #ccc;
      min-height: 400px;
    }

    iframe {
      margin:0;
      padding:0;
      width: 100%;
      height:100%;
      -webkit-box-sizing: border-box;
    }
  }
</style>
