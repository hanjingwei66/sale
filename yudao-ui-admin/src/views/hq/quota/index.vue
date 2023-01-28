<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input v-model="deptName" placeholder="请输入部门名称" clearable size="small" prefix-icon="el-icon-search" style="margin-bottom: 20px"/>
        </div>
        <div class="head-container">
          <el-tree :data="deptOptions" :props="defaultProps" :expand-on-click-node="false" :filter-node-method="filterNode"
                   ref="tree" default-expand-all @node-click="handleNodeClick"/>
        </div>
      </el-col>
      <!--用户数据-->
      <el-col :span="20" :xs="24">

        <el-table v-loading="loading" :data="userList">
          <el-table-column label="用户编号" align="center" key="id" prop="id" v-if="columns[0].visible" />
          <el-table-column label="用户名称" align="center" key="username" prop="username" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="用户昵称" align="center" key="nickname" prop="nickname" v-if="columns[2].visible" :show-overflow-tooltip="true" />
          <el-table-column label="部门" align="center" key="deptName" prop="dept.name" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="手机号码" align="center" key="mobile" prop="mobile" v-if="columns[4].visible" width="120" />
          <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[6].visible" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                         v-hasPermi="['system:user:update']">分配指标</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                    @pagination="getList"/>
      </el-col>
    </el-row>
<!--    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">-->
<!--      <el-form-item label="创建时间">-->
<!--        <el-date-picker v-model="dateRangeCreateTime" style="width: 240px" value-format="yyyy-MM-dd"-->
<!--                        type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" />-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>-->
<!--        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>-->
<!--      </el-form-item>-->
<!--    </el-form>-->

<!--    &lt;!&ndash; 操作工具栏 &ndash;&gt;-->
<!--    <el-row :gutter="10" class="mb8">-->
<!--      <el-col :span="1.5">-->
<!--        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"-->
<!--                   v-hasPermi="['hq:quota:create']">新增</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"-->
<!--                   v-hasPermi="['hq:quota:export']">导出</el-button>-->
<!--      </el-col>-->
<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
<!--    </el-row>-->

<!--    &lt;!&ndash; 列表 &ndash;&gt;-->
<!--    <el-table v-loading="loading" :data="list">-->
<!--      <el-table-column label="id" align="center" prop="id" />-->
<!--      <el-table-column label="指标定额" align="center" prop="quota" />-->
<!--      <el-table-column label="周期 日 周 月 季度 年" align="center" prop="stage" />-->
<!--      <el-table-column label="创建时间" align="center" prop="createTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.createTime) }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="指标定额单位" align="center" prop="unit" />-->
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"-->
<!--                     v-hasPermi="['hq:quota:update']">修改</el-button>-->
<!--          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"-->
<!--                     v-hasPermi="['hq:quota:delete']">删除</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--    </el-table>-->
<!--    &lt;!&ndash; 分页组件 &ndash;&gt;-->
<!--    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"-->
<!--                @pagination="getList"/>-->

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="950px" append-to-body>
      <el-form ref="dynamicValidateForm" :model="dynamicValidateForm" :inline="true" label-width="80px"
               v-for="(quota, index) in dynamicValidateForm.item"
               :key="quota.id"
               >
        <el-form-item label="周期" prop="stage">

          <div class="block">
            <el-date-picker
              v-model="quota.quotaMonth"
              type="month"
              placeholder="选择月">
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="指标定额" prop="quota">
          <el-input v-model="quota.quota" placeholder="请输入指标定额" />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-select v-model="quota.unit" placeholder="请输入单位" clearable>
            <el-option v-for="dict in unitDictDatas"
                       :key="dict.value" :label="dict.label" :value="dict.value"/>
          </el-select>
        </el-form-item>
        <i class="el-icon-circle-plus-outline" @click="addIndex"></i>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { createQuota, updateQuota, deleteQuota, getQuota, getQuotaPage, exportQuotaExcel,getBySaleId,createAndUpdateQuota} from "@/api/hq/quota";
import {
  getQuotaList
} from "@/api/system/user";
import {listUser} from "@/api/system/user";
import {listSimpleDepts} from "@/api/system/dept";
import {listSimplePosts} from "@/api/system/post";
import {getBaseHeader} from "@/utils/request";
import {CommonStatusEnum} from "@/utils/constants";
import {DICT_TYPE, getDictDatas} from "@/utils/dict";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Quota",
  components: { Treeselect },
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
      // 用户表格数据
      userList: null,
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 部门名称
      deptName: undefined,
      // 默认密码
      initPassword: undefined,
      // 日期范围
      dateRange: [],
      // 状态数据字典
      statusOptions: [],
      // 性别状态字典
      sexOptions: [],
      // 岗位选项
      postOptions: [],
      // 角色选项
      roleOptions: [],
      // 表单参数
      form: [{
        saleId :'',
        quota: '',
        stage: '',
        unit: '',
        date: '',
        qutas:[]
      },
      ],
      dynamicValidateForm:{
        item:[{
          id: '',
          saleId :'',
          quota: '',
          stage: '',
          unit: '',
          quotaMonth: ''
        }
        ]
      },
      defaultProps: {
        children: "children",
        label: "name"
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: getBaseHeader(),
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + '/admin-api/system/user/import'
      },
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        username: undefined,
        mobile: undefined,
        status: undefined,
        deptId: undefined
      },
      // 列信息
      columns: [
        { key: 0, label: `用户编号`, visible: true },
        { key: 1, label: `用户名称`, visible: true },
        { key: 2, label: `用户昵称`, visible: true },
        { key: 3, label: `部门`, visible: true },
        { key: 4, label: `手机号码`, visible: true },
        { key: 5, label: `状态`, visible: true },
        { key: 6, label: `创建时间`, visible: true }
      ],
      // 表单校验
      rules: {
      },
      // 是否显示弹出层（角色权限）
      openRole: false,
      saleId: '',
      // 枚举
      SysCommonStatusEnum: CommonStatusEnum,
      // 数据字典
      statusDictDatas: getDictDatas(DICT_TYPE.COMMON_STATUS),
      sexDictDatas: getDictDatas(DICT_TYPE.SYSTEM_USER_SEX),
      unitDictDatas: getDictDatas(DICT_TYPE.HQ_UNIT),
      isAdmin: false,
    };
  },
  created() {
    this.getTreeselect();
    this.getList();
  },
  methods: {
    // 更多操作
    handleCommand(command, index, row) {
      switch (command) {
        case 'handleUpdate':
          this.handleUpdate(row);//修改客户信息p
          break;
        case 'handleDelete':
          this.handleDelete(row);//红号变更
          break;
        case 'handleResetPwd':
          this.handleResetPwd(row);
          break;
        case 'handleRole':
          this.handleRole(row);
          break;
        default:
          break;
      }
    },
    getList() {
      this.loading = true;
      getQuotaList(this.addDateRange(this.queryParams, [
        this.dateRange[0] ? this.dateRange[0] + ' 00:00:00' : undefined,
        this.dateRange[1] ? this.dateRange[1] + ' 23:59:59' : undefined,
      ])).then(response => {
          this.userList = response.data.list;
          this.total = response.data.total;
          this.loading = false;
        }
      );
    },
    /** 查询部门下拉树结构 + 岗位下拉 */
    getTreeselect() {
      listSimpleDepts().then(response => {
        // 处理 deptOptions 参数
        this.deptOptions = [];
        this.deptOptions.push(...this.handleTree(response.data, "id"));
      });
      listSimplePosts().then(response => {
        // 处理 postOptions 参数
        this.postOptions = [];
        this.postOptions.push(...response.data);
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.getList();
    },
    /** 查询列表 */
    // getList() {
    //   this.loading = true;
    //   // 处理查询参数
    //   let params = {...this.queryParams};
    //   this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
    //   // 执行查询
    //   getQuotaPage(params).then(response => {
    //     this.list = response.data.list;
    //     this.total = response.data.total;
    //     this.loading = false;
    //   });
    // },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 表单重置 */
    reset() {
      this.form = {
        id: '',
        saleId: '',
        quota: '',
        stage: '',
        unit: '',
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
      this.title = "分配指标";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      // this.reset();
      const id = row.id;
      this.saleId =row.id
      getBySaleId(id).then(response => {
        if(response.data!=null && response.data.length!=0){
          this.dynamicValidateForm.item = response.data;
          this.dynamicValidateForm.item.forEach( item => item.saleId =id);
        }
        this.open = true;
        this.title = "修改指标";
      });
      // this.form = row.quotas[0];
    },
    /** 提交按钮 */
    submitForm() {
      // this.$refs["dynamicValidateForm"].validate(valid => {
      //   if (!valid) {
      //     return;
      //   }
        // 添加的提交
        createAndUpdateQuota(this.dynamicValidateForm.item).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      // });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除指标编号为"' + id + '"的数据项?').then(function() {
          return deleteQuota(id);
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
      this.$modal.confirm('是否确认导出所有指标数据项?').then(() => {
          this.exportLoading = true;
          return exportQuotaExcel(params);
        }).then(response => {
          this.$download.excel(response, '指标.xls');
          this.exportLoading = false;
        }).catch(() => {});
    },
    addIndex(){
      this.dynamicValidateForm.item.push({
          saleId :this.saleId,
          quota: '',
          stage: '',
          unit: '',
          date: ''
      });
    },
  }
};
</script>
