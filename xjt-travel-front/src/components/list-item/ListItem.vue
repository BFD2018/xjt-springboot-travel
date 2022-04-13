<template>
	<div class="list-item">
		<div class="layout-left">
			<slot name="left-slot"></slot>
		</div>
		<div class="layout-right">
			<slot name="right-slot"></slot>
		</div>
	</div>
</template>

<script>
	export default {
		name: "ListItem"
	}
</script>

<style lang="less" scoped>
	.list-item{
		display: flex;
		.layout-left{
			width: 300px;
			height: 160px;
			overflow: hidden;
		}
		.layout-right{
			flex: 1;
			overflow: hidden;
			margin-left: 20px;
		}
	}
</style>