// pages/user/info/info.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {
      id: null,
      nickName: '',
      avatarUrl: '/images/default-avatar.png', // 默认头像
      phone: '',
      gender: 0, // 0-未知 1-男 2-女
    }, // 用户信息对象
    genderOptions: ['未知', '男', '女'], // 性别选择器选项
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.loadUserInfo();
  },

  /**
   * 加载用户信息
   */
  loadUserInfo: function () {
    const userInfo = wx.getStorageSync('user');
    if (userInfo) {
      this.setData({
        userInfo: {
          id: userInfo.id,
          nickName: userInfo.nickName || '',
          avatarUrl: userInfo.avatarUrl || '/images/default-avatar.png',
          phone: userInfo.phone || '',
          gender: userInfo.gender !== undefined ? userInfo.gender : 0,
        }
      });
    } else {
      this.getUserInfoFromServer();
    }
  },

  /**
   * 从服务器获取用户信息
   */
  getUserInfoFromServer: function () {
    wx.showLoading({
      title: '加载中...',
    });

    const token = wx.getStorageSync('token') || '';
    wx.request({
      url: `${app.globalData.baseUrl}/mini/user/info`,
      method: 'POST',
      header: {
        'Content-Type': 'application/json',
        'token': token
      },
      success: (res) => {
        wx.hideLoading();
        if (res.data && res.data.code === 1) {
          const userData = res.data.data;
          this.setData({
            userInfo: {
              id: userData.id,
              nickName: userData.nickName || '',
              avatarUrl: userData.avatarUrl || '/images/default-avatar.png',
              phone: userData.phone || '',
              gender: userData.gender !== undefined ? userData.gender : 0,
            }
          });
          wx.setStorageSync('user', userData);
        } else {
          wx.showToast({
            title: res.data.msg || '获取用户信息失败',
            icon: 'none'
          });
        }
      },
      fail: (err) => {
        wx.hideLoading();
        console.error('获取用户信息失败:', err);
        wx.showToast({
          title: '网络请求失败',
          icon: 'none'
        });
      }
    });
  },

  /**
   * 通用输入框数据绑定
   * @param {Object} e - 事件对象
   */
  onInput: function (e) {
    const { field } = e.currentTarget.dataset;
    this.setData({
      [`userInfo.${field}`]: e.detail.value
    });
  },

  /**
   * 选择头像
   * @param {Object} e - 事件对象
   */
  chooseAvatar: function (e) {
    const { avatarUrl } = e.detail;

    // 先显示临时头像
    this.setData({
      'userInfo.avatarUrl': avatarUrl,
    });

    // 上传头像到临时目录
    const token = wx.getStorageSync('token') || '';
    wx.uploadFile({
      url: `${app.globalData.baseUrl}/mini/file/upload`,
      filePath: avatarUrl,
      name: 'file',
      header: {
        'token': token
      },
      formData: {
        'type': 'avatar'
      },
      success: (res) => {
        // 正常情况下服务器会返回文件访问路径
        try {
          const data = JSON.parse(res.data);
          if (data.code === 1 && data.data) {
            // 服务器返回的头像URL
            const newAvatarUrl = data.data;
            this.setData({
              'userInfo.avatarUrl': newAvatarUrl
            });
            wx.showToast({
              title: '头像已上传',
              icon: 'success'
            });
          } else {
            wx.showToast({
              title: data.msg || '头像上传失败',
              icon: 'none'
            });
          }
        } catch (e) {
          console.error('解析上传响应失败:', e);
          // 如果服务器暂不支持，则保持使用临时路径
          wx.showToast({
            title: '头像已选择(仅临时)',
            icon: 'none'
          });
        }
      },
      fail: (err) => {
        console.error('上传头像失败:', err);
        wx.showToast({
          title: '头像上传失败',
          icon: 'none'
        });
      }
    });
  },

  /**
   * 性别选择器改变事件
   * @param {Object} e - 事件对象
   */
  bindGenderChange: function (e) {
    this.setData({
      'userInfo.gender': parseInt(e.detail.value)
    });
  },

  /**
   * 提交表单更新用户信息
   */
  submitForm: function () {
    const userInfoToSubmit = this.data.userInfo;

    // 1. 数据校验
    if (!userInfoToSubmit.nickName) {
      wx.showToast({ title: '请输入昵称', icon: 'none' }); return;
    }
    if (userInfoToSubmit.phone && !/^1[3-9]\d{9}$/.test(userInfoToSubmit.phone)) {
      wx.showToast({ title: '请输入正确的手机号', icon: 'none' }); return;
    }

    wx.showLoading({
      title: '保存中...',
    });

    const token = wx.getStorageSync('token') || '';
    wx.request({
      url: `${app.globalData.baseUrl}/mini/user/update`,
      method: 'POST',
      header: {
        'Content-Type': 'application/json',
        'token': token
      },
      data: userInfoToSubmit,
      success: (res) => {
        wx.hideLoading();
        if (res.data && res.data.code === 1) {
          // 更新本地存储的用户信息
          const userInfo = wx.getStorageSync('user') || {};
          Object.assign(userInfo, userInfoToSubmit);
          wx.setStorageSync('user', userInfo);

          wx.showToast({
            title: '更新成功',
            icon: 'success'
          });
          // 成功后返回上一页
          setTimeout(() => {
            wx.navigateBack();
          }, 1500);
        } else {
          wx.showToast({
            title: res.data.msg || '更新失败',
            icon: 'none'
          });
        }
      },
      fail: (err) => {
        wx.hideLoading();
        console.error('更新用户信息失败:', err);
        wx.showToast({
          title: '网络请求失败',
          icon: 'none'
        });
      }
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow () {
    // 设置标题
    wx.setNavigationBarTitle({
      title: '个人信息'
    });
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage () {

  }
})