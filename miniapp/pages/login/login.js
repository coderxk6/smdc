Page({
  data: {
    activeTab: 1,
    username: '',
    password: ''
  },
  switchTab (e) {
    this.setData({
      activeTab: Number(e.currentTarget.dataset.index)
    });
    // 自动填充测试账号
    this.setData({
      username: 'test',
      password: '123456'
    });
  },
  onInputUsername (e) {
    this.setData({ username: e.detail.value });
  },
  onInputPassword (e) {
    this.setData({ password: e.detail.value });
  },
  onLogin () {
    const { username, password } = this.data;
    if (!username || !password) {
      wx.showToast({ title: '请输入账号和密码', icon: 'none' });
      return;
    }
    wx.showLoading({ title: '登录中...' });
    wx.request({
      url: 'http://127.0.0.1:8080/mini/user/account/login',
      method: 'POST',
      data: { username, password },
      header: { 'content-type': 'application/json' },
      success: res => {
        wx.hideLoading();
        if (res.data.code === 1 && res.data.data) {
          wx.setStorageSync('user', res.data.data);
          wx.showToast({ title: '登录成功', icon: 'success' });
          setTimeout(() => {
            wx.reLaunch({ url: '/pages/index/index' });
          }, 500);
        } else {
          wx.showToast({ title: res.data.msg || '登录失败', icon: 'none' });
        }
      },
      fail: () => {
        wx.hideLoading();
        wx.showToast({ title: '网络错误', icon: 'none' });
      }
    });
  },
  onInputConfirm (e) {
    this.onLogin();
  }
}); 