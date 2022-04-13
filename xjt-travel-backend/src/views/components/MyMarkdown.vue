<template>
  <div class="app-container">
    參考github：
    <a href="https://github.com/hinesboy/mavonEditor/blob/master/README.md">mavonEditor</a>

    <div style="margin-top: 20px"></div>
    <mavon-editor v-model="editorValue"
                  class="mavonEditor"
                  ref=md
                  @imgAdd="$imgAdd"
                  @imgDel="$imgDel"
                  :ishljs = "true"/>
  </div>
</template>

<script>

export default {
  name: 'Markdown',
  data() {
    return {
      editorValue:"",
    }
  },
  computed: {

  },
  methods: {
    // 绑定@imgAdd event
    $imgAdd(pos, $file) {
      // 第一步.将图片上传到服务器.
      var formdata = new FormData();
      formdata.append('image', $file);
      this.img_file[pos] = $file;

      this.$request({
        url: '/api/edit/uploadimg',
        method: 'post',
        data: formdata,
        headers: { 'Content-Type': 'multipart/form-data' },
      }).then((res) => {
        let _res = res.data;
        // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
        this.$refs.md.$img2Url(pos, _res.url);
      })
    },
    $imgDel(pos) {
      delete this.img_file[pos];
    },
  },
  mounted() {

  },
  created() {
    console.log(this.$router);
    console.log(this.$route);
  }

}
</script>

<style scoped>
  .mavonEditor{
    width: 95%;
    height: 600px;
  }
</style>
