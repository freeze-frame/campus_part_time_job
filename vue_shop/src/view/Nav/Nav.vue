<template>
  <div class="mynav_container">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item >首页</el-breadcrumb-item>
      <el-breadcrumb-item v-if="second">{{ second }}</el-breadcrumb-item>
      <el-breadcrumb-item v-if="third">{{ third }}</el-breadcrumb-item>
      <el-breadcrumb-item v-if="fourth">{{ fourth }}</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
export default {
  name: 'MyNav',
  props: {
    second: {
      type: String,
      default: null
    },
    third: {
      type: String,
      default: null
    },
    fourth: {
      type: String,
      default: null
    }
  }
}
</script>
<style lang="less" scoped>
.mynav_container {
  margin-bottom: 20px;
}
</style>
