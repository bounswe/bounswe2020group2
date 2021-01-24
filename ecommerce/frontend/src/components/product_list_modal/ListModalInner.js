import './ListModalInner.less'

import React, { useEffect, useState } from 'react'
import { Button, List, notification, Skeleton, Input, Form } from 'antd'
import { sleep } from '../../utils'
import { HeartOutlined, HeartFilled } from '@ant-design/icons'

export const ListModalInner = ({
    onListClick,
    loading,
    lists,
    loadingList,
    onCreateProductListFinish,
    createLoading,
}) => {
    const [form] = Form.useForm()

    if (loading) {
        return (
            <List size="large" itemLayout="horizontal">
                <List.Item key={1} extra={[<Button disabled type="ghost" icon={<HeartOutlined />} />]}>
                    <Skeleton paragraph={{ rows: 1 }} active title={false} />
                </List.Item>
                <List.Item key={2} extra={[<Button disabled type="ghost" icon={<HeartOutlined />} />]}>
                    <Skeleton paragraph={{ rows: 1 }} active title={false} />
                </List.Item>
                <List.Item key={3} extra={[<Button disabled type="ghost" icon={<HeartOutlined />} />]}>
                    <Skeleton paragraph={{ rows: 1 }} active title={false} />
                </List.Item>
            </List>
        )
    }

    const onFinish = async values => {
        await onCreateProductListFinish(values)
        form.resetFields()
    }

    return (
        <List size="large" itemLayout="horizontal">
            {lists.map((list, ix) => {
                return (
                    <List.Item
                        key={list.id ?? ix}
                        className={'choose-list-modal-list'}
                        actions={[
                            <Button
                                loading={list.id === loadingList?.id}
                                type="ghost"
                                icon={list.hasProduct ? <HeartFilled /> : <HeartOutlined />}
                                onClick={onListClick(list)}
                            />,
                        ]}>
                        {list.name ?? `Example list name ${ix}`}
                    </List.Item>
                )
            })}
            <List.Item key={'list-add'} className={'choose-list-modal-list'}>
                <Form form={form} layout="inline" requiredMark={'optional'} onFinish={onFinish}>
                    <Form.Item
                        name="name"
                        label="Create a new product list"
                        rules={[
                            {
                                required: true,
                                message: 'Please enter the name of the list',
                            },
                            {
                                message: 'Make sure the list has at least 3 characters',
                                validator: (rule, val) => {
                                    const _val = val.trim()
                                    if (!_val) return Promise.reject(false)
                                    if (_val.length < 3) return Promise.reject(false)
                                    return Promise.resolve(true)
                                },
                            },
                        ]}>
                        <Input />
                    </Form.Item>

                    <Form.Item>
                        <Button type="primary" htmlType="submit" loading={createLoading}>
                            Create
                        </Button>
                    </Form.Item>
                </Form>
            </List.Item>
        </List>
    )
}
