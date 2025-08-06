<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryRef" :inline="true">
            <el-form-item label="名称" prop="name">
                <el-input v-model="queryParams.name" placeholder="请输入名称" clearable style="width: 200px"
                    @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="所属分类" prop="type">
                <el-cascader v-model="queryParams.type" :options="typeOptions" placeholder="请选择所属分类" clearable
                    style="width: 200px" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
                <el-button icon="Refresh" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="Plus" @click="handleAdd">添加</el-button>
            </el-col>
        </el-row>
        <el-table v-loading="loading" :data="tableData" style="width: 100%">
            <el-table-column prop="name" label="名称" />
            <el-table-column prop="type" label="所属分类">
                <template #default="scope">
                    {{ getType(scope.row.type) }}
                </template>
            </el-table-column>
            <el-table-column prop="name" label="图片">
                <template #default="scope">
                    <ImagePreview :width="100" :height="100" :src="scope.row.url" :preview-src-list="[scope.row.url]" />
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" />
            <el-table-column label="操作" align="center" width="210" class-name="small-padding fixed-width">
                <template #default="scope">
                    <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
                    <el-button v-if="scope.row.parentId == 0" link type="primary" icon="Plus">新增</el-button>
                    <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
            v-model:limit="queryParams.pageSize" @pagination="getList" />
        <el-dialog :title="title" v-model="open" width="800px" append-to-body>
            <el-form ref="photoRef" :model="form" :rules="rules" label-width="110px">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="名称" prop="name">
                            <el-input v-model="form.name" placeholder="请输入名称" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="照片" prop="url">
                            <imageUpload v-model="form.url" :limit="1" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="是否置顶" prop="enabled">
                            <el-radio-group v-model="form.isTop" @change="topChange">
                                <el-radio :label="true">置顶</el-radio>
                                <el-radio :label="false">非置顶</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" v-if="form.isTop">
                        <el-form-item label="排序值" prop="orderNum">
                            <template #label>
                                <span>
                                    <el-tooltip content="值越小 排序越在前" placement="top">
                                        <el-icon><question-filled /></el-icon>
                                    </el-tooltip>
                                </span>
                                显示排序
                            </template>
                            <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="是否展示" prop="enabled">
                            <el-radio-group v-model="form.enabled" @change="topChange">
                                <el-radio :label="true">展示</el-radio>
                                <el-radio :label="false">不展示</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="所属分类" prop="type">
                            <el-cascader v-model="form.type" :options="typeOptions" />
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="primary" @click="submitForm">确 定</el-button>
                    <el-button @click="cancel">取 消</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>
<script setup>
import { ref } from "vue";
import { listPhoto, addPhoto, updatePhoto, delPhoto, infoPhoto } from "@/api/manager/photo";
import { list } from "@/api/manager/photoType";
const { proxy } = getCurrentInstance();
const title = ref("");
const total = ref(0);
const loading = ref(false);
const tableData = ref([])
const open = ref(false)
const typeOptions = ref([])
const data = reactive({
    form: {

    },
    queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        type: undefined,
        isAsc: "asc,desc",
        orderByColumn: "orderNum,createTime"
    },
    rules: {
        name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
        type: [{ required: true, message: "所属分类不能为空", trigger: "blur" }],
        url: [{ required: true, message: "照片不能为空", trigger: "blur" }],
    },
});
const { queryParams, form, rules } = toRefs(data);

function handleShowDetail(detail) {
    console.log('handleShowDetail');
    showDetail.value = true
    goodsDetail.value = detail
}

function getType(type) {
    if (type) {
        for (let r of typeOptions.value) {
            if (type == r.value) {
                return r.label;
            }
        }
    }
}

/** 搜索按钮操作 */
function handleQuery() {
    getList();
}
function resetQuery() {
    proxy.resetForm("queryRef");
    handleQuery();
}
function reset() {
    form.value = {
        name: undefined,
        url: undefined,
        type: undefined,
        orderNum: 9999,
        isTop: false,
        enabled: true
    };
    proxy.resetForm("photoRef");
}

function buildTypeOptions(types) {
    const opt = []
    for (let t of types) {
        opt.push({
            label: t.name,
            value: t.id
        })
    }
    typeOptions.value = opt
}
function handleAdd() {
    title.value = "添加照片"
    open.value = true
    reset();
}

/** 取消按钮 */
function cancel() {
    open.value = false;
    reset();
}

function handleUpdate(item) {
    open.value = true
    reset();
    infoPhoto(item.id).then(res => {
        form.value = res.data;
        open.value = true;
        form.value.isTop = form.value.orderNum != 9999
        title.value = "修改照片";
    })
}

function getList() {
    loading.value = true;
    let q = { ...queryParams.value }
    q.type = q.type?.toString();
    listPhoto(q).then(res => {
        tableData.value = res.data.records
        total.value = res.data.total;
        loading.value = false;
    })
}

function submitForm() {
    proxy.$refs["photoRef"].validate(valid => {
        if (valid) {
            form.value.type = form.value.type.toString();
            if (form.value.id != undefined) {
                updatePhoto(form.value).then(response => {
                    proxy.$modal.msgSuccess("修改成功");
                    open.value = false;
                    getList();
                });
            } else {
                addPhoto(form.value).then(response => {
                    proxy.$modal.msgSuccess("新增成功");
                    open.value = false;
                    getList();
                });
            }
        }
    });
}
function handleDelete(row) {
    proxy.$modal.confirm('是否确认删除照片名称为"' + row.name + '"的数据项?').then(function () {
        return delPhoto(row.id);
    }).then(() => {
        getList();
        proxy.$modal.msgSuccess("删除成功");
    }).catch(() => { });
}

function topChange(e) {
    form.value.orderNum = e ? 0 : 9999
}
getList();
list().then(res => {
    buildTypeOptions(res.data)
})
</script>