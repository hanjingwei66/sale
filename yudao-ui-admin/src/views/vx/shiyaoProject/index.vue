<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目名称" prop="projectName">
        <el-input v-model="queryParams.projectName" placeholder="请输入项目名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="地址" prop="address">
<!--        <el-input v-model="queryParams.address" placeholder="请输入地址" clearable @keyup.enter.native="handleQuery"/>-->
        <el-cascader
          style="width:250px"
          v-model="queryParams.address"
          :options="options"
          :props="{label:'name',value:'areaCode',children:'children',checkStrictly: true ,expandTrigger: 'hover'}"
          clearable></el-cascader>
      </el-form-item>
      <el-form-item label="招募状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择招募状态" clearable size="small">
          <el-option v-for="dict in this.getDictDatas(DICT_TYPE.RECRUIT_STATUS)"
                       :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="项目分类" prop="category">
        <el-select v-model="queryParams.category" placeholder="请输入项目分类" clearable size="small">
          <el-option v-for="dict in this.getDictDatas(DICT_TYPE.PROJECT_CATEGORY)"
                     :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
<!--        <el-input v-model="queryParams.category" placeholder="请输入项目分类" clearable @keyup.enter.native="handleQuery"/>-->
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
                   v-hasPermi="['vx:shiyao-project:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['vx:shiyao-project:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="物品名称" align="center" prop="goodsName" />
      <el-table-column label="地址" align="center" prop="address" :show-overflow-tooltip="true">
<!--        <template slot-scope="scope">-->
<!--          <el-cascader-->
<!--            style="width:250px"-->
<!--            v-model="form.address"-->
<!--            :options="options"-->
<!--            :props="{label:'name',value:'areaCode',children:'children',checkStrictly: true ,expandTrigger: 'hover'}"-->
<!--            clearable></el-cascader>-->
<!--        </template>-->
      </el-table-column>>

      <el-table-column label="地址详情" align="center" prop="addressDetails" :show-overflow-tooltip="true"/>
      <el-table-column label="性别" align="center" prop="sex">
        <template slot-scope="scope">
          <dict-tag :type="DICT_TYPE.SYSTEM_USER_SEX" :value="scope.row.sex" />
        </template>
      </el-table-column>
      <el-table-column label="入院天数" align="center" prop="inpDays" />
<!--      <el-table-column label="男性价格" align="center" prop="priceMan" />-->
<!--      <el-table-column label="女性价格" align="center" prop="priceWoman" />-->
<!--      <el-table-column label="最小年龄" align="center" prop="minAge" />-->
<!--      <el-table-column label="最大年龄" align="center" prop="maxAge" />-->
<!--      <el-table-column label="最小BMI值" align="center" prop="minBmi" />-->
<!--      <el-table-column label="最大BMI值" align="center" prop="maxBmi" />-->
      <el-table-column label="招募状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :type="DICT_TYPE.RECRUIT_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="项目分类" align="center" prop="category">
        <template slot-scope="scope">
          <dict-tag :type="DICT_TYPE.PROJECT_CATEGORY" :value="scope.row.category" />
        </template>
      </el-table-column>
      <el-table-column label="是否烟检" align="center" prop="isSmoke">
        <template slot-scope="scope">
          <dict-tag :type="DICT_TYPE.IS_SMOKE" :value="scope.row.isSmoke" />
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="到场时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="截至时间" align="center" prop="stopTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.stopTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="详情" align="center" prop="context" :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['vx:shiyao-project:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['vx:shiyao-project:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" >
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="物品名称" prop="goodsName">
          <el-input v-model="form.goodsName" placeholder="请输入物品名称" />
        </el-form-item>
        <el-form-item label="地址" prop="address" >
<!--          <el-input v-model="form.address" placeholder="请输入地址" />-->
          <el-cascader
            style="width:250px"
            v-model="form.address"
            :options="options"
            :props="{label:'name',value:'areaCode',children:'children',checkStrictly: true ,expandTrigger: 'hover'}"
            clearable></el-cascader>
        </el-form-item>
<!--        <div class="block">-->
<!--          <span class="demonstration">地址:</span>-->
<!--          <el-cascader-->
<!--            :options="options"-->
<!--            :props="{ checkStrictly: true }"-->
<!--            clearable></el-cascader>-->
<!--        </div>-->
        <el-form-item label="地址详情" prop="addressDetails">
          <el-input v-model="form.addressDetails" placeholder="请输入地址详情" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio v-for="dict in this.getDictDatas(DICT_TYPE.SYSTEM_USER_SEX)"
                      :key="dict.value" :label="parseInt(dict.value)">{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="入院天数" prop="inpDays">
          <el-input v-model="form.inpDays" placeholder="请输入入院天数" />
        </el-form-item>
        <el-form-item label="男性价格" prop="priceMan" v-if="form.sex==2 || form.sex==1">
          <el-input v-model="form.priceMan" placeholder="请输入男性价格" />
        </el-form-item>
        <el-form-item label="女性价格" prop="priceWoman" v-if="form.sex==2 || form.sex==0">
          <el-input v-model="form.priceWoman" placeholder="请输入女性价格" />
        </el-form-item>
        <el-form-item label="最小年龄" prop="minAge">
          <el-input v-model="form.minAge" placeholder="请输入最小年龄" />
        </el-form-item>
        <el-form-item label="最大年龄" prop="maxAge">
          <el-input v-model="form.maxAge" placeholder="请输入最大年龄" />
        </el-form-item>
        <el-form-item label="最小BMI值" prop="minBmi">
          <el-input v-model="form.minBmi" placeholder="请输入最小BMI值" />
        </el-form-item>
        <el-form-item label="最大BMI值" prop="maxBmi">
          <el-input v-model="form.maxBmi" placeholder="请输入最大BMI值" />
        </el-form-item>
        <el-form-item label="招募状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择招募状态" clearable size="small">
            <el-option v-for="dict in this.getDictDatas(DICT_TYPE.RECRUIT_STATUS)"
                       :key="dict.value" :label="dict.label" :value="dict.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="项目分类" prop="category">
          <el-select v-model="form.category" placeholder="请输入项目分类" clearable size="small">
            <el-option v-for="dict in this.getDictDatas(DICT_TYPE.PROJECT_CATEGORY)"
                       :key="dict.value" :label="dict.label" :value="dict.value"/>
          </el-select>
        </el-form-item>

        <el-form-item label="是否烟检" prop="isSmoke">
<!--          <el-input v-model="form.isSmoke" placeholder="请输入是否烟检" />-->
          <el-select v-model="form.isSmoke" placeholder="请输入项目分类" clearable size="small">
            <el-option v-for="dict in this.getDictDatas(DICT_TYPE.IS_SMOKE)"
                       :key="dict.value" :label="dict.label" :value="dict.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="到场时间" prop="startTime">
          <el-date-picker clearable v-model="form.startTime" type="date" value-format="timestamp" placeholder="选择到场时间" />
        </el-form-item>
        <el-form-item label="截至时间" prop="stopTime">
          <el-date-picker clearable v-model="form.stopTime" type="date" value-format="timestamp" placeholder="选择截至时间" />
        </el-form-item>
        <el-form-item label="详情">
          <editor v-model="form.context" :min-height="192"/>
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
import { createShiyaoProject, updateShiyaoProject, deleteShiyaoProject, getShiyaoProject, getShiyaoProjectPage, exportShiyaoProjectExcel } from "@/api/vx/shiyaoProject";
import Editor from '@/components/Editor';
import { getDictArea } from '@/api/system/dict/data'

export default {
  name: "ShiyaoProject",
  components: {
    Editor,
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
      // 试药项目列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      elCascaderWidth: '250px',
      dateRangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        projectName: null,
        address: null,
        status: null,
        category: null,
      },
      options: [{
        value: 'zhinan',
        label: '指南',
        children: [{
          value: 'shejiyuanze',
          label: '设计原则',
          children: [{
            value: 'yizhi',
            label: '一致'
          }, {
            value: 'fankui',
            label: '反馈'
          }, {
            value: 'xiaolv',
            label: '效率'
          }, {
            value: 'kekong',
            label: '可控'
          }]
        }, {
          value: 'daohang',
          label: '导航',
          children: [{
            value: 'cexiangdaohang',
            label: '侧向导航'
          }, {
            value: 'dingbudaohang',
            label: '顶部导航'
          }]
        }]
      }],
        // 表单参数
      form: {},
      // 表单校验
      rules: {
        projectName: [{ required: true, message: "项目名称不能为空", trigger: "blur" }],
        address: [{ required: true, message: "地址不能为空", trigger: "blur" }],
        sex: [{ required: true, message: "性别不能为空", trigger: "blur" }],
      }
    };
  },
  created() {
    this.getList();
    this.loadArea();
  },
  methods: {
    loadArea(){
      getDictArea().then(response => {
        this.options =response.data
      })
    },
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 处理查询参数

      let params = {...this.queryParams};
      if(this.queryParams.address!=null){
        console.log(this.queryParams.address)
        params.address=this.queryParams.address.join()
      }

      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行查询
      getShiyaoProjectPage(params).then(response => {
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
        projectName: undefined,
        goodsName: undefined,
        address: undefined,
        addressDetails: undefined,
        sex: undefined,
        inpDays: undefined,
        priceMan: undefined,
        priceWoman: undefined,
        minAge: undefined,
        maxAge: undefined,
        minBmi: undefined,
        maxBmi: undefined,
        status: undefined,
        category: undefined,
        isSmoke: undefined,
        sort: undefined,
        startTime: undefined,
        stopTime: undefined,
        context: undefined,
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
      this.title = "添加试药项目";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getShiyaoProject(id).then(response => {
        this.form = response.data;
        this.form.address =response.data.address.split(",")
        this.open = true;
        this.title = "修改试药项目";
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
          if(this.form.address!=null){
            this.form.address = this.form.address.toString()
          }
          updateShiyaoProject(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        if(this.form.address!=null){
          this.form.address = this.form.address.toString()
        }
        // 添加的提交
        createShiyaoProject(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除试药项目编号为"' + id + '"的数据项?').then(function() {
          return deleteShiyaoProject(id);
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
      this.$modal.confirm('是否确认导出所有试药项目数据项?').then(() => {
          this.exportLoading = true;
          return exportShiyaoProjectExcel(params);
        }).then(response => {
          this.$download.excel(response, '试药项目.xls');
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
