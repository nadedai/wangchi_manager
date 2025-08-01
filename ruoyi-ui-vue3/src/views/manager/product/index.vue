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
                    <el-button link type="primary" icon="Delete">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :title="title" v-model="open" width="680px" append-to-body>
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
                            <el-cascader v-model="typeOption" :options="typeOptions" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="商品详细介绍" prop="detail">
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

        <el-dialog v-model="showDetail" title="商品详情" append-to-body width="800px">
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
const loading = ref(false);
const tableData = ref([])
const open = ref(false)
const typeOptions = ref([])
const typeOption = ref([])
const showDetail = ref(false)
const goodsDetail = ref()
const data = reactive({
    form: {

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
    },
});
const { queryParams, form, rules } = toRefs(data);

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
        detail: undefined
    };
    typeOption.value = []
    proxy.resetForm("productRef");
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

function handleUpdate(item) {
    open.value = true
    reset();
    infoProduct(item.id).then(res => {
        form.value = res.data;
        typeOption.value = res.data.productType.split(",")
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
        loading.value = false;
    })
}

function submitForm() {
    proxy.$refs["productRef"].validate(valid => {
        if (valid) {
            form.value.productType = typeOption.value.toString();
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
getList()
list().then(res => {
    buildTypeOptions(res.data)
})
</script>