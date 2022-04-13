<template>
  <div class="my-yamlEditor-view">
    <h2>Yaml編輯器</h2>
    <div class="editor-container">
      <yaml-editor  v-model="value" />
    </div>
  </div>
</template>

<script>
  import YamlEditor from '@/components/YamlEditor/index.vue';

  const yamlData = "- hosts: all\n  become: yes\n  become_method: sudo\n  gather_facts: no\n\n  tasks:\n  - name: \"install {{ package_name }}\"\n    package:\n      name: \"{{ package_name }}\"\n      state: \"{{ state | default('present') }}\"";

  export default {
    name: 'MyYamlEdit',
    components: {
      YamlEditor,
    },
    data() {
      return {
        value: yamlData,
      };
    },
  };
</script>
<style scoped>
  .my-yamlEditor-view{
    margin: 0;
    text-align: left;
    font-size: 14px;
  }
  .editor-container{
    position: relative;
    height: 100%;
  }
</style>
