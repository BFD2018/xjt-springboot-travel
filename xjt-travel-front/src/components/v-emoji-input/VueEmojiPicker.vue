<template>
	<div class="v-emoji-picker-view">
		<el-row>
			<el-col>
				<el-input
						id="input"
						v-model="inputText"
						type="textarea"
						:autosize="{ minRows: 4, maxRows: 10}"
						resize="both"
						placeholder="请输入内容">
				</el-input>
			</el-col>
		</el-row>
		<el-row>
			<el-col :span="8">
				<el-button type="success" plain size="mini" @click="showDialog = !showDialog">😃</el-button>
			</el-col>
			<el-col :span="4" :offset="12">
				<el-button @click="sumitComment" size="mini" type="success">发表评论</el-button>
			</el-col>
		</el-row>

		<VEmojiPicker v-show="showDialog"
									:style="{ width: '440px', height: '200px' }"
									labelSearch="Search"
									lang="pt-BR"
									@select="selectEmoji"/>

	</div>
</template>

<script>
	export default {
		name: "VueEmojiPicker",
		data(){
			return{
				showDialog:false,
				inputText:"",
			}
		},
		methods:{
			selectEmoji(emoji){
				//console.log(emoji);
				let input = document.getElementById("input")
				let startPos = input.selectionStart
				let endPos = input.selectionEnd
				let resultText = input.value.substring(0, startPos) + emoji.data + input.value.substring(endPos)
				input.value = resultText
				input.focus()
				input.selectionStart = startPos + emoji.data.length
				input.selectionEnd = startPos + emoji.data.length
				this.inputText = resultText
			},
			sumitComment(){
				//console.log(this.inputText);
				this.$emit("sumit_comment",this.inputText);
				this.inputText = "";
			}
		},
	}
</script>

<style scoped>
	.v-emoji-picker-view{
		width: 100%;
		height: 360px;
	}
</style>