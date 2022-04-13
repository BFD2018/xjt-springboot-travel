<template>
	<div class="mainPage" ref="MainRef">
		<aplayer
				:autoplay="autoplay"
				:music="songInfo"
				:showLrc="showLrc"
				:mutex="mutex"
				:theme="theme"
				:shuffle="shuffle"
				:repeat="repeat"
				:listFolded="listFolded"
				:listMaxHeight="listMaxHeight"
				:list="list"
				:controls="controls"
				:muted="muted"
				:volume="volume"
				:preload="preload"
		></aplayer>


	</div>
</template>

<script>
	import Aplayer from 'vue-aplayer'

	export default {
		name:"APlayer",
		props: {
			/**
			 *  音频配置
			 * */
			songInfo: {
				type: Object,
				default: () => {
					return {
						title: '', //歌曲名称
						artist: ' ', //演唱者
						lrc: '', //LRC 歌词或者歌词文件的 URL
						pic: '', //封面图片 URL
						src: '' //音频文件的 URL
					}
				}
			},
			/**
			 *  播放列表。如果 list 不是空数组，播放列表就会显示出来，即使 list 中只有一首歌并且它和 music 一样
			 * */
			list: {
				type: Array,
				default: () => {
					return []
				}
			},
			/**
			 *  是否展示歌词
			 * */
			showLrc: {
				type: Boolean,
				default: false
			},
			/**
			 *  自动播放。如果多个 mutex 播放器设置了 autoplay，只有第一个会自动播放
			 * */
			autoplay: {
				type: Boolean,
				default: true
			},
			/**
			 *  显示原生 audio 元素（在播放器面板和播放列表面板之间）
			 * */
			controls: {
				type: Boolean,
				default: false
			},
			/**
			 *  静音
			 * */
			muted: {
				type: Boolean,
				default: false
			},
			/**
			 *  是否在该播放器播放时暂停其他播放器
			 * */
			mutex: {
				type: Boolean,
				default: true
			},
			/**
			 *  随机播放
			 * */
			shuffle: {
				type: Boolean,
				default: false
			},
			/**
			 *  默认收起播放列表
			 * */
			listFolded: {
				type: Boolean,
				default: false
			},
			/**
			 *  主题色。如果当前歌曲也设置了 theme 则以歌曲的为准
			 * */
			theme: {
				type: String,
				default: '#41b883'
			},
			/**
			 *  轮播模式。值可以是 'repeat-one'（单曲循环）'repeat-all'（列表循环）或者 'no-repeat'（不循环）。
			 *  为了好记，还可以使用对应的 'music' 'list' 'none'
			 * */
			repeat: {
				type: String,
				default: 'no-repeat'
			},
			/**
			 * 播放列表面板最大高度
			 * */
			listMaxHeight: {
				type: String,
				default: ''
			},
			/**
			 * 加载音乐的方式可以是“none”“metadata”或“auto”
			 * */
			preload: {
				type: String,
				default: 'none'
			},
			/**
			 * 播放音量
			 * */
			volume: {
				type: Number,
				default: 0.8
			},

		},
		watch: {},
		data() {
			return {

			}
		},
		created() {
		},
		mounted() {
		},
		methods: {

		},
		components: {
			Aplayer
		},
		beforeDestroy() {
		}

	}
</script>

<style lang='less' scoped>
	.mainPage{
		width: 100%;
		height: 84px;
		background: #FCFCFC;
		border: 1px solid #E0E0E0;
		border-radius: 4px;
	}
</style>