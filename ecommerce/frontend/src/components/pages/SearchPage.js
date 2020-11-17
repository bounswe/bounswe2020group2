import './SearchPage.less'
import { CategoryBar } from '../CategoryBar'
import { useAppContext } from '../../context/AppContext'
import { Button, Form, Select, Slider, Rate, Pagination } from 'antd'
import { categories, productSortBy, subcategories, brands, vendors } from '../../utils'
import { useState } from 'react'
import { ProductCard } from '../product_card/ProductCard'

const formItemLayout = {
    labelCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 8,
        },
    },
    wrapperCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 16,
        },
    },
}
const tailFormItemLayout = {
    wrapperCol: {
        xs: {
            span: 24,
            offset: 0,
        },
        sm: {
            span: 16,
            offset: 8,
        },
    },
}

export const SearchPage = () => {
    const p = {
        title: 'Title',
        rating: '5',
        price: '30.00',
        currency: 'TL',
        imageUrl: 'https://picsum.photos/300',
        width: 300,
        productId: 'product-id',
    }
    return (
        <div className={'search-page'}>
            <div className="search-page-side-panel">
                <SearchSidePanel />
            </div>
            <div className="search-page-results">
                <SearchResults
                    products={[p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p].map((x, ix) => ({
                        ...x,
                        id: ix,
                    }))}
                />
            </div>
        </div>
    )
}

export const SearchResults = ({ products, pagination, onPaginationChanged }) => {
    return (
        <div>
            <div className="product-grid">
                {products.map(product => (
                    <ProductCard key={product.id} product={product} />
                ))}
            </div>
            <Pagination pagination={pagination} onChange={onPaginationChanged} hideOnSinglePage />
        </div>
    )
}

export const SearchSidePanel = ({ initialValues = {}, onSubmit = () => {} }) => {
    const [form] = Form.useForm()
    const [category, setCategory] = useState(initialValues?.category ?? 'electronics')

    const onSelectCategory = category => {
        setCategory(category)
        form.setFieldsValue({ subcategory: null })
    }

    const onFinish = console.log

    return (
        <Form {...formItemLayout} form={form} initialValues={initialValues} onFinish={onFinish}>
            <Form.Item name="category" label="Category">
                <Select onSelect={onSelectCategory} placeholder="Category...">
                    {Object.entries(categories).map(([key, value]) => (
                        <Select.Option key={key} value={key}>
                            {value}
                        </Select.Option>
                    ))}
                </Select>
            </Form.Item>
            <Form.Item name="subcategory" label="Subcategory">
                <Select placeholder="Subcategory..." allowClear>
                    {Object.entries(subcategories[category]).map(([key, value]) => (
                        <Select.Option key={key} value={key}>
                            {value}
                        </Select.Option>
                    ))}
                </Select>
            </Form.Item>
            <Form.Item name="vendor" label="Vendor">
                <Select placeholder="Vendor..." allowClear>
                    {Object.entries(vendors).map(([key, value]) => (
                        <Select.Option key={key} value={key}>
                            {value}
                        </Select.Option>
                    ))}
                </Select>
            </Form.Item>
            <Form.Item name="brand" label="Brand">
                <Select placeholder="Brand..." allowClear>
                    {Object.entries(brands).map(([key, value]) => (
                        <Select.Option key={key} value={key}>
                            {value}
                        </Select.Option>
                    ))}
                </Select>
            </Form.Item>
            <Form.Item name="priceRange" label="Price range">
                <Slider range />
            </Form.Item>
            <Form.Item name="avgCustomerReview" label="Rating">
                <Rate allowHalf allowClear />
            </Form.Item>

            <Form.Item name="sortBy" label="Sort by">
                <Select placeholder="Sort by..." allowClear>
                    {Object.entries(productSortBy).map(([key, value]) => (
                        <Select.Option key={key} value={key}>
                            {value}
                        </Select.Option>
                    ))}
                </Select>
            </Form.Item>
            <Form.Item {...tailFormItemLayout}>
                <Button type="primary" htmlType="submit">
                    Submit
                </Button>
            </Form.Item>
        </Form>
    )
}
