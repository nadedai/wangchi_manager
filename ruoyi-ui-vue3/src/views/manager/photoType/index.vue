<template>
    <div class="app-container">
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="Plus" @click="handleAdd">新增</el-button>
            </el-col>
        </el-row>

        <el-table v-if="refreshTable" v-loading="loading" :data="tableData" style="width: 100%"
            :default-expand-all="isExpandAll" row-key="id">
            <el-table-column prop="name" label="分类名称" />
            <el-table-column prop="orderNum" label="排序" />
            <el-table-column prop="enabled" label="状态">
                <template #default="scope">
                    <el-tag v-if="scope.row.enabled">正常</el-tag>
                    <el-tag v-if="!scope.row.enabled" type="danger">停用</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" />

            <el-table-column label="操作" align="center" width="210" class-name="small-padding fixed-width">
                <template #default="scope">
                    <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
                    <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 添加或修改菜单对话框 -->
        <el-dialog :title="title" v-model="open" width="680px" append-to-body>
            <el-form ref="photoTypeRef" :model="form" :rules="rules" label-width="100px">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="分类名" prop="name">
                            <el-input v-model="form.name" placeholder="请输入分类名" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="状态" prop="enabled">
                            <el-radio-group v-model="form.enabled">
                                <el-radio :label="false">停用</el-radio>
                                <el-radio :label="true">正常</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="显示排序" prop="orderNum">
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
import { list, add, update, del, info } from "@/api/manager/photoType";
import { ref } from "vue";
const refreshTable = ref(true);
const loading = ref(false);
const tableData = ref([])
const open = ref(false)
const title = ref()
const data = reactive({
    form: {},
    rules: {
        name: [{ required: true, message: "分类名称不能为空", trigger: "blur" }],
        enabled: [{ required: true, message: "状态不能为空", trigger: "blur" }],
        orderNum: [{ required: true, message: "显示排序不能为空", trigger: "blur" }],
    },
});
const { proxy } = getCurrentInstance();
const { queryParams, form, rules } = toRefs(data);

function getList() {
    loading.value = true;
    list().then((res => {
        tableData.value = res.data
        loading.value = false;
    }))
}

function reset() {
    form.value = {
        name: undefined,
        enabled: true,
        orderNum: 0
    };

    proxy.resetForm("productTypeRef");
}

function handleAdd() {
    title.value = "添加分类"
    open.value = true
    reset()

}


function submitForm() {
    proxy.$refs["photoTypeRef"].validate(valid => {
        if (valid) {
            if (form.value.id != undefined) {
                update(form.value).then(response => {
                    proxy.$modal.msgSuccess("修改成功");
                    open.value = false;
                    getList();
                });
            } else {
                add(form.value).then(response => {
                    proxy.$modal.msgSuccess("新增成功");
                    open.value = false;
                    getList();
                });
            }
        }
    });
}

function handleUpdate(item) {
    open.value = true
    reset();
    info(item.id).then(res => {
        form.value = res.data;
        open.value = true;
        title.value = "修改分类";
    })
}

function cancel() {
    open.value = false;
    reset();
}
function handleDelete(row) {
    proxy.$modal.confirm('是否确认删除名称为"' + row.name + '"的数据项?').then(function () {
        return del(row.id);
    }).then(() => {
        getList();
        proxy.$modal.msgSuccess("删除成功");
    }).catch(() => { });
}
getList()
</script>
