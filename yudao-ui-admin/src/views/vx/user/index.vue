<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="微信id" prop="vxId">
        <el-input v-model="queryParams.vxId" placeholder="请输入微信id" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="姓名" prop="userName">
        <el-input v-model="queryParams.userName" placeholder="请输入姓名" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="证件号" prop="userCardId">
        <el-input v-model="queryParams.userCardId" placeholder="请输入证件号" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="dateRangeCreateTime" style="width: 240px" value-format="yyyy-MM-dd"
                        type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['vx:user:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['vx:user:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="微信id" align="center" prop="vxId" />
      <el-table-column label="姓名" align="center" prop="userName" />
      <el-table-column label="证件号" align="center" prop="userCardId" />
      <el-table-column label="电话" align="center" prop="userPhone" />
      <el-table-column label="性别" align="center" prop="sex">
        <template slot-scope="scope">
          <dict-tag :type="DICT_TYPE.SYSTEM_USER_SEX" :value="scope.row.sex" />
        </template>
      </el-table-column>
      <el-table-column label="身高" align="center" prop="height" />
      <el-table-column label="体重" align="center" prop="weight" />
      <el-table-column label="是否抽烟" align="center" prop="isSmoke">
        <template slot-scope="scope">
          <dict-tag :type="DICT_TYPE.INFRA_BOOLEAN_STRING" :value="scope.row.isSmoke" />
        </template>
      </el-table-column>
      <el-table-column label="常驻地址" align="center" prop="address" />
      <el-table-column label="详细地址" align="center" prop="addressDetail" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="是否提供身份证" align="center" prop="isCard">
        <template slot-scope="scope">
          <dict-tag :type="DICT_TYPE.INFRA_BOOLEAN_STRING" :value="scope.row.isCard" />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleSingUp(scope.row)"
                     >报名</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['vx:user:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['vx:user:delete']">删除</el-button>

        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="微信id" prop="vxId">
          <el-input v-model="form.vxId" placeholder="请输入微信id" />
        </el-form-item>
        <el-form-item label="姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="证件号" prop="userCardId">
          <el-input v-model="form.userCardId" placeholder="请输入证件号" />
        </el-form-item>
        <el-form-item label="电话" prop="userPhone">
          <el-input v-model="form.userPhone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio v-for="dict in this.getDictDatas(DICT_TYPE.SYSTEM_USER_SEX)"
                      :key="dict.value" :label="parseInt(dict.value)">{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="身高" prop="height">
          <el-input v-model="form.height" placeholder="请输入身高" />
        </el-form-item>
        <el-form-item label="体重" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入体重" />
        </el-form-item>
        <el-form-item label="是否抽烟" prop="isSmoke">
          <el-radio-group v-model="form.isSmoke">
            <el-radio v-for="dict in this.getDictDatas(DICT_TYPE.INFRA_BOOLEAN_STRING)"
                      :key="dict.value" :label="parseInt(dict.value)">{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="常驻地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入常驻地址" />
        </el-form-item>
        <el-form-item label="详细地址" prop="addressDetail">
          <el-input v-model="form.addressDetail" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="是否提供身份证" prop="isCard">
          <el-radio-group v-model="form.isCard">
            <el-radio v-for="dict in this.getDictDatas(DICT_TYPE.INFRA_BOOLEAN_STRING)"
                      :key="dict.value" :label="parseInt(dict.value)">{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="项目列表" prop="project">
          <el-select v-model="form.projectId" placeholder="请选择项目" clearable size="small">
            <el-option v-for="project in projects"
                       :key="project.projectId" :label="project.projectName" :value="project.projectId"/>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {createUser, updateUser, deleteUser, getUser, getUserPage, exportUserExcel, signUpUser} from "@/api/vx/user";
import { getProjectList } from "@/api/vx/shiyaoProject";
import form from "@/views/bpm/form";
export default {
  name: "User",
  components: {
  },
  data() {
    return {
      projects: [
        {
        projectId: '',
        projectName: ''
        }
      ],
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 微信用户信息列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dateRangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        vxId: null,
        userName: null,
        userCardId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        vxId: [{ required: true, message: "微信id不能为空", trigger: "blur" }],
        userName: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
        userCardId: [{ required: true, message: "证件号不能为空", trigger: "blur" }],
        address: [{ required: true, message: "常驻地址不能为空", trigger: "blur" }],
      }
    };
  },
  created() {
    this.getList();
    this.initProject();
  },
  methods: {
    initProject(){
      getProjectList().then(response => {
        this.projects =response.data
      })
    },
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 处理查询参数
      let params = {...this.queryParams};
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行查询
      getUserPage(params).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 表单重置 */
    reset() {
      this.form = {
        id: undefined,
        vxId: undefined,
        userName: undefined,
        userCardId: undefined,
        userPhone: undefined,
        sex: undefined,
        height: undefined,
        weight: undefined,
        isSmoke: undefined,
        address: undefined,
        addressDetail: undefined,
        remark: undefined,
        isCard: undefined,
        projectId: undefined,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRangeCreateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加微信用户信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getUser(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改微信用户信息";
      });
    },
    handleSingUp(row){
      this.reset();
      const id = row.id;
      getUser(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "报名";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        if(this.form.projectId!=null){
          this.form.userId=this.form.id
          signUpUser(this.form).then(response =>{
            this.$modal.msgSuccess("成功");
            this.open = false;
            this.getList();
          })
        }
        // 修改的提交
        if (this.form.id != null) {
          updateUser(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createUser(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除微信用户信息编号为"' + id + '"的数据项?').then(function() {
          return deleteUser(id);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      // 处理查询参数
      let params = {...this.queryParams};
      params.pageNo = undefined;
      params.pageSize = undefined;
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行导出
      this.$modal.confirm('是否确认导出所有微信用户信息数据项?').then(() => {
          this.exportLoading = true;
          return exportUserExcel(params);
        }).then(response => {
          this.$download.excel(response, '微信用户信息.xls');
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
