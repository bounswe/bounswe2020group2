import './SearchSidePanel.less'

import { Button, Form, Rate, Select, Slider } from 'antd'
import { useState } from 'react'

import { brands, categories, productSortBy, subcategories, vendors } from '../../utils'

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

export const SearchSidePanel = ({ initialValues = {}, onSubmit = () => {} }) => {
    const [form] = Form.useForm()
    const [category, setCategory] = useState(initialValues?.category ?? 'electronics')

    const onSelectCategory = category => {
        setCategory(category)
        form.setFieldsValue({ subcategory: null })
    }

    const onFinish = values => {
        console.log('Search side panel form:', values)
        onSubmit(values)
    }

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
            <Form.Item name="rating" label="Rating">
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
