<template>
  <div class="mycome_container">
    <img src="../assets/3574.jpg" alt="">
    <h1 class="my-h1">欢迎来到校园兼职平台——商家端</h1>
  </div>
</template>

<script>
export default {
  name: 'MyWecome'
}
</script>

<style lang="less" scoped>
.mycome_container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  .my-h1{
    color: #e2d3d3;
    font-size: 50px;
    line-height: 300px;
  }
  .el-container{
    height: 100%;
  }
  img{
    width: 100%;
  }
  .my_page {
    color: #FFF;
    font-size: 36px;
    font-weight: 800;
    
  }
}
</style>
