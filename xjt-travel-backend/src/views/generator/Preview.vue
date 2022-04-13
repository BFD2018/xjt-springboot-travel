<template>
  <div class="preview-view">
    <Java :value="content" :height="height" />
  </div>
</template>

<script>
import Java from '@/components/JavaEdit/index'

export default {
  name: 'Preview',
  components: { Java },
  data() {
    return {
      content: "",
      height: '',
    }
  },
  created() {
    this.height = document.documentElement.clientHeight - 180 + 'px'
    const tableName = this.$route.params.tableName

    this.$getRequest({
      url: '/generator/' + tableName + '/' + type,
      method: 'post',
      responseType: ''
    }).then(res =>{
      downloadFile(res, tableName, 'zip');
    })

    this.$postRequest(
        'api/generator/' + tableName + '/' + type,
        {
          responseType:''
        }
    ).then(res =>{
      console.log(res);
    }).catch(() => {
      this.$router.go(-1)
    })
  }
}
</script>
