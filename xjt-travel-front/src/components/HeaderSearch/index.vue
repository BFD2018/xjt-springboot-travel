<template>
	<div class="header-search">
		<el-input placeholder="请输入内容"
							prefix-icon="el-icon-search"
							v-model="inputSearchKey" class="input-with-select">
			<el-button slot="append" type="success" @click="handleSearch">搜索</el-button>
		</el-input>
	</div>
</template>

<script>
	export default {
		name: 'HeaderSearch',
		data() {
			return {
				inputSearchKey: '',
			}
		},
		methods: {
			handleSearch() {
				this.$emit("headerSearch",this.inputSearchKey);
			},
		}
	}
</script>

<style lang="less" scoped>

</style>
