import './SearchSidePanel.less'

import { Button, Form, InputNumber, Rate, Select, Slider } from 'antd'
import { useState } from 'react'

import { brands, categories, productSortBy, subcategories, vendors } from '../../utils'
import ButtonGroup from 'antd/lib/button/button-group'
import { intersection } from 'ramda'

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
    const onResetClick = () => {
        form.resetFields()
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
            <Form.Item name="brand" label="Brand">
                <Select placeholder="Brand..." allowClear>
                    {Object.entries(brands).map(([key, value]) => (
                        <Select.Option key={key} value={key}>
                            {value}
                        </Select.Option>
                    ))}
                </Select>
            </Form.Item>
            <Form.Item name="maxPrice" label="Max price">
                <InputNumber min={1} max={10_000_000} step={5} />
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
                <ButtonGroup>
                    <Button type="link" htmlType="button" onClick={onResetClick}>
                        Reset
                    </Button>
                    <Button type="primary" htmlType="submit">
                        Submit
                    </Button>
                </ButtonGroup>
            </Form.Item>
        </Form>
    )
}
