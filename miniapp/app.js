App({
  globalData: {
    baseUrl: 'http://localhost:8080' // 请替换为您的后端服务地址
  },
  onLaunch () {
    // 小程序初始化逻辑
    console.log('App Launch');

    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
  }
}); 