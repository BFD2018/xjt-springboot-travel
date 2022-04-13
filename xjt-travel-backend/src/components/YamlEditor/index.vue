<template>
	<div class="yaml-editor">
		<textarea ref="textarea" />
	</div>
</template>

<script>
	import CodeMirror from 'codemirror'
	import 'codemirror/addon/lint/lint.css'
	import 'codemirror/lib/codemirror.css'
	import 'codemirror/theme/monokai.css'
	import 'codemirror/mode/yaml/yaml'
	import 'codemirror/addon/lint/lint'
	import 'codemirror/addon/lint/yaml-lint'

	window.jsyaml = require('js-yaml') // 引入js-yaml为codemirror提高语法检查核心支持

	export default {
		name: 'YamlEditor',
		// eslint-disable-next-line vue/require-prop-types
		props: ['value'],
		data() {
			return {
				yamlEditor: false
			}
		},
		watch: {
			value(value) {
				const editorValue = this.yamlEditor.getValue()
				if (value !== editorValue) {
					this.yamlEditor.setValue(this.value)
				}
			}
		},
		mounted() {
			this.yamlEditor = CodeMirror.fromTextArea(this.$refs.textarea, {
				lineNumbers: true, // 显示行号
				mode: 'text/x-yaml', // 语法model
				gutters: ['CodeMirror-lint-markers'],  // 语法检查器
				theme: 'monokai', // 编辑器主题
				lint: true // 开启语法检查
			})

			this.yamlEditor.setValue(this.value)
			this.yamlEditor.on('change', (cm) => {
				this.$emit('changed', cm.getValue())
				this.$emit('input', cm.getValue())
			})
		},
		methods: {
			getValue() {
				return this.yamlEditor.getValue()
			}
		}
	}
</script>

<style scoped>
	.yaml-editor{
		height: 100%;
		position: relative;
	}
	.yaml-editor >>> .CodeMirror {
		height: auto;
		min-height: 300px;
	}
	.yaml-editor >>> .CodeMirror-scroll{
		min-height: 300px;
	}
	.yaml-editor >>> .cm-s-rubyblue span.cm-string {
		color: #F08047;
	}
</style>
