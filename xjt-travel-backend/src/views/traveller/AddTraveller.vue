<template>
  <div class="traveller-add-view">
    <h2>新增旅行家信息</h2>
    <el-form ref="editForm"
             status-icon
             label-width="80px"
             label-position="left"
             :model="editForm">
      <el-form-item label="名字"
                    :rules="[
											{ required: true, message: '请输入名称', trigger: 'blur' },
										]">
        <el-input v-model="editForm.name"></el-input>
      </el-form-item>

      <el-form-item label="描述">
        <el-input v-model="editForm.description"></el-input>
      </el-form-item>

      <el-form-item label="头像Url">
        <el-input v-model="editForm.avatar"></el-input>
      </el-form-item>

      <el-form-item label="预览头像">
        <img v-lazy="editForm.avatar" alt=""
             style="width: 160px; height: 160px;border: 4px solid #fafafa;">
      </el-form-item>

      <el-form-item style="margin: auto;">
        <el-button type="info" @click="reset">重置</el-button>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    name: "AddTraveller",
    data() {
      return {
        editForm: {
          name: "",
          description: "",
          avatar: "",
        },
      }
    },
    methods: {
      submitForm() {
        console.log(this.editForm);
        this.$postRequest("/traveller/insert", this.editForm).then(res => {
          if (res.data.status === 200) {
            this.$notify.success(res.data.msg);
            this.$router.push('/traveller/list')
          } else {
            this.$notify.success(res.data.msg);
            return false;
          }
        });
      },
      //重置（清空输入）
      reset() {
        this.$refs.editForm.resetFields();
      },

    },
    created() {

    }
  }
</script>

<style lang="less" scoped>
  .traveller-add-view {
    padding: 20px 0;
    margin: 100px auto;
    width: 50%;
  }
</style>
