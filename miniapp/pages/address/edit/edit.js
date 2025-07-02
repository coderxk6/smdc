Page({
  /**
   * 页面的初始数据
   */
  data: {
    address: {
      id: null,
      name: '',
      phone: '',
      province: '',
      city: '',
      district: '',
      detail: '',
      isDefault: 0 // 0-非默认，1-默认
    }, // 当前编辑的地址对象
    region: ['', '', ''], // 省市区选择器绑定数据
    isEditMode: false, // 是否处于编辑模式
  },

  /**
   * 生命周期函数--监听页面加载
   * @param {Object} options - 页面启动参数，可能包含地址ID (id)
   */
  onLoad: function (options) {
    if (options.id) {
      // 如果传入ID，说明是编辑模式
      this.setData({
        isEditMode: true
      });
      this.loadAddressDetail(options.id);
    }
  },

  /**
   * 加载地址详情（编辑模式下使用）
   * @param {string} id - 地址ID
   */
  loadAddressDetail: async function (id) {
    try {
      const baseUrl = 'http://localhost:8080'; // 替换为你的后端服务地址
      const token = wx.getStorageSync('token') || '';

      // 注意：这里假设后端有/mini/user/address/info/{id}接口，如果不存在，请创建
      const res = await wx.request({
        url: `${baseUrl}/mini/user/address/info/${id}`,
        method: 'GET',
        header: {
          'Content-Type': 'application/json',
          'token': token
        },
      });

      if (res && res.data && res.data.code === 1) {
        const addressData = res.data.data;
        this.setData({
          address: addressData,
          region: [addressData.province, addressData.city, addressData.district]
        });
      } else {
        wx.showToast({
          title: (res && res.data && res.data.msg) || '加载地址详情失败',
          icon: 'none'
        });
      }
    } catch (error) {
      console.error('加载地址详情失败:', error);
      wx.showToast({
        title: '网络请求失败',
        icon: 'none'
      });
    }
  },

  /**
   * 省市区选择器改变事件
   * @param {Object} e - 事件对象
   */
  regionChange: function (e) {
    const region = e.detail.value;
    this.setData({
      region: region,
      'address.province': region[0],
      'address.city': region[1],
      'address.district': region[2],
    });
  },

  /**
   * 默认地址开关改变事件
   * @param {Object} e - 事件对象
   */
  onDefaultSwitchChange: function (e) {
    this.setData({
      'address.isDefault': e.detail.value ? 1 : 0
    });
  },

  /**
   * 表单提交事件
   * @param {Object} e - 事件对象，包含表单数据
   */
  submitForm: async function (e) {
    const formData = e.detail.value;
    // 更新data中的address对象，确保最新数据
    this.setData({
      'address.name': formData.name,
      'address.phone': formData.phone,
      'address.detail': formData.detail,
      'address.isDefault': formData.isDefault ? 1 : 0
    });

    const addressToSubmit = this.data.address;

    // 1. 数据校验
    if (!addressToSubmit.name) {
      wx.showToast({ title: '请输入收货人姓名', icon: 'none' }); return;
    }
    if (!addressToSubmit.phone || !/^1[3-9]\d{9}$/.test(addressToSubmit.phone)) {
      wx.showToast({ title: '请输入正确的手机号', icon: 'none' }); return;
    }
    if (!addressToSubmit.province || !addressToSubmit.city || !addressToSubmit.district) {
      wx.showToast({ title: '请选择所在地区', icon: 'none' }); return;
    }
    if (!addressToSubmit.detail) {
      wx.showToast({ title: '请输入详细地址', icon: 'none' }); return;
    }

    try {
      const baseUrl = 'http://localhost:8080';
      const token = wx.getStorageSync('token') || '';
      const url = this.data.isEditMode ?
        `${baseUrl}/mini/user/address/update` :
        `${baseUrl}/mini/user/address/add`;
      const method = 'POST';

      const res = await wx.request({
        url: url,
        method: method,
        header: {
          'Content-Type': 'application/json',
          'token': token
        },
        data: addressToSubmit
      });

      if (res && res.data && res.data.code === 1) {
        wx.showToast({
          title: this.data.isEditMode ? '更新成功' : '添加成功',
          icon: 'success'
        });
        // 成功后返回上一页
        setTimeout(() => {
          wx.navigateBack();
        }, 1500);
      } else {
        wx.showToast({
          title: (res && res.data && res.data.msg) || '操作失败',
          icon: 'none'
        });
      }
    } catch (error) {
      console.error('提交地址失败:', error);
      wx.showToast({
        title: '网络请求失败',
        icon: 'none'
      });
    }
  },

  /**
   * 删除地址（仅在编辑模式下显示）
   */
  deleteAddress: function () {
    const addressId = this.data.address.id;
    if (!addressId) return;

    wx.showModal({
      title: '提示',
      content: '确定要删除该地址吗？',
      success: async (res) => {
        if (res.confirm) {
          try {
            const baseUrl = 'http://localhost:8080';
            const token = wx.getStorageSync('token') || '';
            const result = await wx.request({
              url: `${baseUrl}/mini/user/address/delete/${addressId}`,
              method: 'POST',
              header: {
                'Content-Type': 'application/json',
                'token': token
              },
            });

            if (result && result.data && result.data.code === 1) {
              wx.showToast({
                title: '删除成功',
                icon: 'success'
              });
              setTimeout(() => {
                wx.navigateBack(); // 删除成功后返回地址列表页
              }, 1500);
            } else {
              wx.showToast({
                title: (result && result.data && result.data.msg) || '删除失败',
                icon: 'none'
              });
            }
          } catch (error) {
            console.error('删除地址失败:', error);
            wx.showToast({
              title: '网络错误',
              icon: 'none'
            });
          }
        }
      }
    });
  }
}) 