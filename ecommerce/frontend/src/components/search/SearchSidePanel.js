import './SearchSidePanel.less'

import { Button, Form, InputNumber, Rate, Select } from 'antd'
import ButtonGroup from 'antd/lib/button/button-group'

import { useAppContext } from '../../context/AppContext'
import { brands, productSortBy } from '../../utils'

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
    const { categories } = useAppContext()
    const [form] = Form.useForm()

    const onFinish = values => {
        console.log('Search side panel form:', values)
        onSubmit(values)
    }

    const onResetClick = () => form.resetFields()

    const onFieldsChange = fields => {
        if (fields.find(field => field.name[0] == 'category')) form.setFieldsValue({ subcategory: undefined })
    }

    return (
        <Form
            {...formItemLayout}
            form={form}
            onFieldsChange={onFieldsChange}
            initialValues={initialValues}
            onFinish={onFinish}>
            <Form.Item name="category" label="Category">
                <Select placeholder="Category..." allowClear>
                    {categories.map(category => (
                        <Select.Option key={category.id} value={category.id}>
                            {category.name}
                        </Select.Option>
                    ))}
                </Select>
            </Form.Item>
            <Form.Item label="Subcategory" dependencies={['category']}>
                {({ getFieldValue }) => {
                    const category = categories.find(category => category.id == getFieldValue('category'))
                    const subcategories = category?.subcategories ?? []
                    return (
                        <Form.Item name="subcategory" noStyle>
                            <Select placeholder="Subcategory..." allowClear>
                                {subcategories.map(subcategory => (
                                    <Select.Option key={subcategory.id} value={subcategory.id}>
                                        {subcategory.name}
                                    </Select.Option>
                                ))}
                            </Select>
                        </Form.Item>
                    )
                }}
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
