<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户名称" prop="customerName">
        <el-input v-model="queryParams.customerName" placeholder="请输入客户名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="客户电话" prop="telphone">
        <el-input v-model="queryParams.telphone" placeholder="请输入客户电话" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>

<!--      <el-form-item label="创建时间">-->
<!--        <el-date-picker v-model="dateRangeCreateTime" style="width: 240px" value-format="yyyy-MM-dd"-->
<!--                        type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" />-->
<!--      </el-form-item>-->
      <el-form-item label="帐号状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择帐号状态" clearable size="small">
            <el-option v-for="dict in this.getDictDatas(DICT_TYPE.HQ_USER_STATUS)"
                       :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
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
                  >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="客户id" align="center" prop="id" />
      <el-table-column label="客户名称" align="center" prop="customerName" />
      <el-table-column label="客户电话" align="center" prop="telphone" />
      <el-table-column label="性别" align="center" prop="sex">
        <template slot-scope="scope">
          <dict-tag :type="DICT_TYPE.SYSTEM_USER_SEX" :value="scope.row.sex" />
        </template>
      </el-table-column>
      <el-table-column label="订单金额" align="center" prop="orderMoney" />
      <el-table-column label="单位" align="center" prop="unit" >
        <template slot-scope="scope">
          <dict-tag :type="DICT_TYPE.HQ_UNIT" :value="scope.row.unit" />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="帐号状态" align="center" prop="status" >
        <template slot-scope="scope">
          <dict-tag :type="DICT_TYPE.HQ_USER_STATUS" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="用户邮箱" align="center" prop="email" />
      <el-table-column label="分配状态" align="center" prop="allocationStatus" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     >修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客户名称" prop="customerName">
          <el-input v-model="form.customerName" placeholder="请输入客户名称" />
        </el-form-item>
        <el-form-item label="客户电话" prop="telphone">
          <el-input v-model="form.telphone" placeholder="请输入客户电话" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio v-for="dict in this.getDictDatas(DICT_TYPE.SYSTEM_USER_SEX)"
                      :key="dict.value" :label="dict.value">{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="订单金额" prop="orderMoney">
          <el-input v-model="form.orderMoney" placeholder="请输入订单金额" />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-select v-model="form.unit" placeholder="请输入单位" clearable>
            <el-option v-for="dict in this.getDictDatas(DICT_TYPE.HQ_UNIT)"
                       :key="dict.value" :label="dict.label" :value="dict.value"/>
          </el-select>
        </el-form-item>

        <el-form-item label="用户邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入用户邮箱" />
        </el-form-item>
        <el-form-item label="账号状态" prop="allocationStatus">
          <el-radio-group v-model="form.status">
            <el-radio v-for="dict in this.getDictDatas(DICT_TYPE.HQ_USER_STATUS)"
            :key="dict.value" :label="parseInt(dict.value)">{{dict.label}}</el-radio>
          </el-radio-group>
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
import { createCustomer, updateCustomer, deleteCustomer, getCustomer, getCustomerPage, exportCustomerExcel } from "@/api/hq/customer";

export default {
  name: "Customer",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // hq用户列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dateRangeLoginDate: [],
      dateRangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        customerName: null,
        telphone: null,
        sex: null,
        orderMoney: null,
        unit: null,
        loginIp: null,
        status: null,
        email: null,
        allocationStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        status: [{ required: true, message: "帐号状态（0正常 1停用）不能为空", trigger: "blur" }],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 处理查询参数
      let params = {...this.queryParams};
      this.addBeginAndEndTime(params, this.dateRangeLoginDate, 'loginDate');
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行查询
      getCustomerPage(params).then(response => {
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
        customerName: undefined,
        telphone: undefined,
        sex: undefined,
        orderMoney: undefined,
        unit: undefined,
        loginIp: undefined,
        loginDate: undefined,
        status: undefined,
        email: undefined,
        allocationStatus: undefined,
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
      this.dateRangeLoginDate = [];
      this.dateRangeCreateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加hq用户";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getCustomer(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改hq用户";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        // 修改的提交
        if (this.form.id != null) {
          updateCustomer(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createCustomer(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除hq用户编号为"' + id + '"的数据项?').then(function() {
          return deleteCustomer(id);
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
      this.addBeginAndEndTime(params, this.dateRangeLoginDate, 'loginDate');
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行导出
      this.$modal.confirm('是否确认导出所有hq用户数据项?').then(() => {
          this.exportLoading = true;
          return exportCustomerExcel(params);
        }).then(response => {
          this.$download.excel(response, 'hq用户.xls');
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
