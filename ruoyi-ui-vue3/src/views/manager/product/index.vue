<template>

    <div class="app-container">
        <el-form :model="queryParams" ref="queryRef" :inline="true">
            <el-form-item label="产品编号" prop="goodsSn">
                <el-input v-model="queryParams.goodsSn" placeholder="请输入产品编号" clearable style="width: 200px"
                    @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="产品名称" prop="name">
                <el-input v-model="queryParams.name" placeholder="请输入产品名称" clearable style="width: 200px"
                    @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="所属分类" prop="productType">
                <el-cascader v-model="queryParams.productType" :options="typeOptions" placeholder="请选择所属分类" clearable
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
            <el-table-column prop="goodsSn" label="产品编号" />
            <el-table-column prop="name" label="产品名称" />
            <el-table-column prop="productType" label="所属分类">
                <template #default="scope">
                    {{ getProductType(scope.row.productType) }}
                </template>
            </el-table-column>
            <el-table-column prop="name" label="图片">
                <template #default="scope">
                    <ImagePreview :width="100" :height="100" :src="scope.row.picUrl"
                        :preview-src-list="[scope.row.picUrl]" />
                </template>
            </el-table-column>
            <el-table-column prop="counterPrice" label="专柜价格(元)" />
            <el-table-column prop="price" label="当前价格(元)" />
            <el-table-column prop="createTime" label="创建时间" />
            <el-table-column label="操作" align="center" width="210" class-name="small-padding fixed-width">
                <template #default="scope">
                    <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
                    <el-button link type="primary" icon="View"
                        @click="handleShowDetail(scope.row.detail)">查看</el-button>
                    <el-button v-if="scope.row.parentId == 0" link type="primary" icon="Plus">新增</el-button>
                    <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
            v-model:limit="queryParams.pageSize" @pagination="getList" />
        <el-dialog :title="title" v-model="open" width="800px" append-to-body>
            <el-form ref="productRef" :model="form" :rules="rules" label-width="110px">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="产品编号" prop="goodsSn">
                            <el-input v-model="form.goodsSn" placeholder="请输入产品编号" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="产品名称" prop="name">
                            <el-input v-model="form.name" placeholder="请输入产品名称" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="专柜价格(元)" prop="counterPrice">
                            <el-input-number v-model="form.counterPrice" placeholder="专柜价格" />

                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="当前价格(元)" prop="price">
                            <el-input-number v-model="form.price" placeholder="当前价格" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="是否新品" prop="isNew">
                            <el-radio-group v-model="form.isNew">
                                <el-radio :label="true">新品</el-radio>
                                <el-radio :label="false">非新品</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="是否在售" prop="isSale">
                            <el-radio-group v-model="form.isSale">
                                <el-radio :label="true">在售</el-radio>
                                <el-radio :label="false">未售</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="产品图片" prop="picUrl">
                            <imageUpload v-model="form.picUrl" :limit="1" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="宣传画廊" prop="gallery">
                            <imageUpload v-model="form.gallery" :limit="5" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="所属分类" prop="productType">
                            <el-cascader v-model="form.productType" :options="typeOptions" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="产品参数">
                            <el-button type="primary" plain @click="handleAddAttr">添加</el-button>
                            <el-table :data="form.attrs" style="width: 100%">
                                <el-table-column prop="attrName" label="参数名" />
                                <el-table-column prop="attrValue" label="参数值" />
                                <el-table-column label="操作" min-width="120">
                                    <template #default="scope">
                                        <el-button link type="primary" size="small" @click="handleAttrDel(scope.row)">
                                            删除
                                        </el-button>
                                        <el-button link type="primary" size="small"
                                            @click="handleAttrEdit(scope.row)">编辑</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="产品详细介绍" prop="detail">
                            <editor v-model="form.detail" :min-height="500" />
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

        <el-dialog :title="titleAttr" v-model="openAttr" width="400px" append-to-body>
            <el-form ref="productAttrRef" :model="formAttr" :rules="attrRules">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="参数名" prop="attrName">
                            <el-input v-model="formAttr.attrName" placeholder="请输入参数名" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="参数值" prop="attrValue">
                            <el-input v-model="formAttr.attrValue" placeholder="请输入参数值" />
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="primary" @click="submitAttr">确 定</el-button>
                    <el-button @click="cancelAttr">取 消</el-button>
                </div>
            </template>
        </el-dialog>

        <el-dialog v-model="showDetail" title="产品详情" append-to-body width="800px">
            <div v-html="goodsDetail"></div>
        </el-dialog>
    </div>
</template>
<script setup>
import { ref } from "vue";
import { list } from "@/api/manager/productType";
import { listProduct, addProduct, updateProduct, delProduct, infoProduct } from "@/api/manager/product";
const { proxy } = getCurrentInstance();
const title = ref("");
const titleAttr = ref("");
const total = ref(0);
const loading = ref(false);
const tableData = ref([])
const open = ref(false)
const openAttr = ref(false)
const typeOptions = ref([])
const showDetail = ref(false)
const goodsDetail = ref()
const data = reactive({
    form: {

    },
    formAttr: {

    },
    queryParams: {
        pageNum: 1,
        pageSize: 10,
        goodsSn: undefined,
        name: undefined,
        productType: undefined,
        isAsc: "desc",
        orderByColumn: "createTime"
    },
    rules: {
        goodsSn: [{ required: true, message: "产品编号不能为空", trigger: "blur" }],
        name: [{ required: true, message: "产品名称不能为空", trigger: "blur" }],
        price: [{ required: true, message: "当前价格不能为空", trigger: "blur" }],
        productType: [{ required: true, message: "所属分类不能为空", trigger: "blur" }],
        picUrl: [{ required: true, message: "产品图片不能为空", trigger: "blur" }],
        gallery: [{ required: true, message: "宣传画廊不能为空", trigger: "blur" }],
    },
    attrRules: {
        attrName: [{ required: true, message: "参数名不能为空", trigger: "blur" }],
        attrValue: [{ required: true, message: "参数值名称不能为空", trigger: "blur" }],
    }
});
const { queryParams, form, formAttr, rules, attrRules } = toRefs(data);

function handleShowDetail(detail) {
    console.log('handleShowDetail');
    showDetail.value = true
    goodsDetail.value = detail
}

function getProductType(type) {
    if (type) {
        const l = type.split(',')
        let s = ""
        for (const r of typeOptions.value) {
            if (r.value == l[0]) {
                s += r.label
                r.children.map(t => {
                    if (t.value == l[1]) {
                        s += " / "
                        s += t.label
                    }
                })
            }
        }
        return s
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
        goodsSn: undefined,
        name: undefined,
        counterPrice: undefined,
        price: undefined,
        isNew: false,
        isSale: true,
        picUrl: undefined,
        gallery: undefined,
        productType: undefined,
        detail: undefined,
        attrs: []
    };
    proxy.resetForm("productRef");
}

function resetAttr() {
    formAttr.value = {
        attrName: undefined,
        attrValue: undefined
    };

    console.log('formAttr', formAttr.value)
    proxy.resetForm("productAttrRef");
}

function buildTypeOptions(types) {
    const opt = []
    for (const type of types) {
        const temp = {
            label: type.name,
            value: type.id,
            children: []
        }
        for (const c of type.children) {
            const temp1 = {
                label: c.name,
                value: c.id,
            }
            temp.children.push(temp1)
        }
        opt.push(temp)
    }
    typeOptions.value = opt
}
function handleAdd() {
    title.value = "添加产品"
    open.value = true
    reset();
}

/** 取消按钮 */
function cancel() {
    open.value = false;
    reset();
}

function cancelAttr() {
    openAttr.value = false
    resetAttr()
}

function submitAttr() {
    proxy.$refs["productAttrRef"].validate(valid => {
        if (valid) {
            if (titleAttr.value.includes("添加")) {
                form.value.attrs = [...form.value.attrs, { ...formAttr.value }];
                openAttr.value = false;
            } else {
                let len = form.value.attrs.length
                let i = delAttr(formAttr.value.original)
                form.value.attrs = [...form.value.attrs.slice(0, i), { ...formAttr.value }, ...form.value.attrs.slice(i, len)];
                openAttr.value = false;
            }
        }
    })
}

function handleAttrEdit(row) {
    titleAttr.value = '编辑产品参数'
    formAttr.value.attrName = row.attrName
    formAttr.value.attrValue = row.attrValue
    formAttr.value.original = row
    openAttr.value = true
}

function handleAttrDel(row) {
    proxy.$modal.confirm('是否确认删除参数名称为"' + row.attrName + '"的数据项?').then(
        () => {
            delAttr(row)
        }
    ).catch(() => { });
}

function delAttr(row) {
    for (let i = 0; i < form.value.attrs.length; i++) {
        if (form.value.attrs[i].attrName == row.attrName) {
            form.value.attrs.splice(i, 1)
            form.value.attrs = [...form.value.attrs]
            return i;
        }
    }
}

function handleUpdate(item) {
    open.value = true
    reset();
    infoProduct(item.id).then(res => {
        form.value = res.data;
        form.value.productType = res.data.productType.split(",")
        open.value = true;
        title.value = "修改产品";
    })
}

function getList() {
    loading.value = true;
    let q = { ...queryParams.value }
    q.productType = q.productType?.toString();
    listProduct(q).then(res => {
        tableData.value = res.data.records
        total.value = res.data.total;
        loading.value = false;
    })
}

function submitForm() {
    proxy.$refs["productRef"].validate(valid => {
        if (valid) {
            form.value.productType = form.value.productType.toString();
            if (form.value.id != undefined) {
                updateProduct(form.value).then(response => {
                    proxy.$modal.msgSuccess("修改成功");
                    open.value = false;
                    getList();
                });
            } else {
                addProduct(form.value).then(response => {
                    proxy.$modal.msgSuccess("新增成功");
                    open.value = false;
                    getList();
                });
            }
        }
    });
}
function handleDelete(row) {
    proxy.$modal.confirm('是否确认删除产品名称为"' + row.name + '"的数据项?').then(function () {
        return delProduct(row.id);
    }).then(() => {
        getList();
        proxy.$modal.msgSuccess("删除成功");
    }).catch(() => { });
}

function handleAddAttr() {
    titleAttr.value = '添加产品参数'
    openAttr.value = true
    resetAttr()
}

getList()
list().then(res => {
    buildTypeOptions(res.data)
})
</script>