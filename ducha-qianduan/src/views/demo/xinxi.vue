<template>
  <div class="face-management">
    <div class="header">
      <h2>人脸管理</h2>
      <el-button type="primary" @click="openAddDialog">点击添加人员信息</el-button>
    </div>

    <el-table
      :data="persons"
      style="width: 100%"
      border
      v-loading="loading"
    >
      <el-table-column prop="index" label="#" width="60">
        <template slot-scope="scope">{{ scope.$index + 1 }}</template>
      </el-table-column>

      <el-table-column prop="name" label="姓名" width="140" />
      <el-table-column prop="idCard" label="身份证号" min-width="180" />
      <el-table-column prop="faceCard" label="人脸卡号" width="160" />
      <el-table-column prop="phone" label="联系电话" width="140" />
      <el-table-column prop="department" label="部门/单位" min-width="140" />

      <el-table-column label="头像" width="120">
        <template slot-scope="scope">
          <img v-if="scope.row.photoUrl" :src="scope.row.photoUrl" class="avatar" />
          <span v-else class="no-avatar">无</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" fixed="right" width="160">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="openEditDialog(scope.$index, scope.row)">修改</el-button>
          <el-button type="text" size="small" style="color:#f56c6c" @click="removePerson(scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- Add / Edit Dialog -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="600px">
      <el-form :model="form" :rules="rules" ref="personForm" label-width="110px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>

        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="form.idCard" placeholder="请输入身份证号" />
        </el-form-item>

        <el-form-item label="人脸卡号" prop="faceCard">
          <el-input v-model="form.faceCard" placeholder="请输入人脸卡号" />
        </el-form-item>

        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话" />
        </el-form-item>

        <el-form-item label="部门/单位" prop="department">
          <el-input v-model="form.department" placeholder="请输入部门或单位" />
        </el-form-item>

        <el-form-item label="头像">
          <el-upload
            class="avatar-uploader"
            action=""
            :show-file-list="false"
            :before-upload="beforeAvatarUpload"
            :on-change="handleAvatarChange"
            :auto-upload="false"
          >
            <img v-if="form.photoUrl" :src="form.photoUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-upload-icon"></i>
          </el-upload>
          <div style="margin-top:8px;color:#999;font-size:12px">建议人脸照片：正脸、白底、jpg/png</div>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitPerson">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'FaceManagement',
  data() {
    return {
      loading: false,
      persons: [
        // 示例数据（前端暂时写死）
        {
          name: '张三',
          idCard: '110101199001011234',
          faceCard: 'FC-0001',
          phone: '13800001111',
          department: '营部',
          photoUrl: '',
        },
        {
          name: '李四',
          idCard: '110101198512129876',
          faceCard: 'FC-0002',
          phone: '13800002222',
          department: '警卫连',
          photoUrl: '',
        },
      ],

      dialogVisible: false,
      isEdit: false,
      editIndex: -1,
      form: {
        name: '',
        idCard: '',
        faceCard: '',
        phone: '',
        department: '',
        photoUrl: '',
      },
      rules: {
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        idCard: [{ required: true, message: '请输入身份证号', trigger: 'blur' }],
        faceCard: [{ required: true, message: '请输入人脸卡号', trigger: 'blur' }],
      },
    }
  },
  computed: {
    dialogTitle() {
      return this.isEdit ? '修改人员信息' : '添加人员信息'
    }
  },
  methods: {
    openAddDialog() {
      this.isEdit = false
      this.editIndex = -1
      this.resetForm()
      this.dialogVisible = true
      this.$nextTick(() => { this.$refs.personForm && this.$refs.personForm.clearValidate() })
    },
    openEditDialog(index, row) {
      this.isEdit = true
      this.editIndex = index
      this.form = Object.assign({}, row)
      this.dialogVisible = true
      this.$nextTick(() => { this.$refs.personForm && this.$refs.personForm.clearValidate() })
    },
    removePerson(index) {
      this.$confirm('确定删除该人员信息吗？', '提示', {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.persons.splice(index, 1)
        this.$message({ type: 'success', message: '删除成功' })
      }).catch(() => {})
    },
    submitPerson() {
      this.$refs.personForm.validate((valid) => {
        if (!valid) return
        if (this.isEdit && this.editIndex > -1) {
          this.$set(this.persons, this.editIndex, Object.assign({}, this.form))
          this.$message({ type: 'success', message: '修改成功' })
        } else {
          this.persons.push(Object.assign({}, this.form))
          this.$message({ type: 'success', message: '添加成功' })
        }
        this.dialogVisible = false
      })
    },
    resetForm() {
      this.form = {
        name: '',
        idCard: '',
        faceCard: '',
        phone: '',
        department: '',
        photoUrl: '',
      }
    },

    // avatar upload handlers (client-side preview only)
    beforeAvatarUpload(file) {
      const isJPGorPNG = file.type === 'image/jpeg' || file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPGorPNG) {
        this.$message.error('上传头像图片只能是 JPG/PNG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPGorPNG && isLt2M
    },
    handleAvatarChange(file) {
      // 生成本地预览（不上传）
      const reader = new FileReader()
      reader.onload = (e) => {
        this.form.photoUrl = e.target.result
      }
      reader.readAsDataURL(file.raw)
    }
  }
}
</script>

<style scoped>
.face-management { padding: 18px; background: #fff; border-radius: 6px; }
.header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px; }
.avatar { width: 56px; height: 56px; border-radius: 4px; object-fit: cover; }
.no-avatar { color: #999; }
.avatar-uploader { display: inline-block; width: 56px; height: 56px; border: 1px dashed #d9d9d9; border-radius: 4px; text-align: center; line-height: 56px; }
.avatar-upload-icon { font-size: 28px; color: #c0c4cc; }
.dialog-footer { text-align: right; }
</style>
