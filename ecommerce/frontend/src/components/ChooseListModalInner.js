import './ChooseListModalInner.less'

import React, { useEffect, useState } from 'react'
import { Button, List, notification } from 'antd'
import { sleep } from '../utils'
import { HeartOutlined, HeartFilled } from '@ant-design/icons'

export const ChooseListModalInner = ({ product, onChooseList }) => {
    const [lists, setLists] = useState([])
    const [loading, setLoading] = useState(true)

    const getLists = async (product) => {
        try {
            setLoading(true)
            await sleep(1000)
            // backend call to get lists of user

            setLists([
                {
                    id: 1,
                    name: 'Electronics',
                    hasProduct: true,
                },

                {
                    id: 2,
                    name: 'Thanksgiving',
                    hasProduct: false,
                },
                {
                    id: 3,
                    name: 'Blackfriday',
                    hasProduct: true,
                },
            ])
        } catch (error) {
        } finally {
            setLoading(false)
        }
    }

    useEffect(() => {
        getLists(product)
    }, [])

    const removeProductFromList = async (product, list) => {
        try {
            await sleep(1000) // actual code here
            notification.success({description: "Added product to list"})
        } catch (error) {
            notification.error({description: "Failed to remove product from list"})

        } finally {

        }
    }

    const addProductToList = async (product, list) => {
        try {
            await sleep(1000) // actual code here
            notification.success({description: "Added product to list"})
        } catch (error) {
            notification.error({description: "Failed to remove product from list"})
        } finally {

        }
    }

    const onListClick = list => async () => {
        if (list.hasProduct) {
            await removeProductFromList(list)
        } else {
            await addProductToList(list)
        }
        await getLists(product)
    }

    return (
        <List loading={loading} size="large" itemLayout="horizontal">
            {lists.map((list, ix) => {
                return (
                    <List.Item
                        key={list.id ?? ix}
                        className={'choose-list-modal-list'}
                        actions={[
                            <Button
                                type="ghost"
                                icon={list.hasProduct ? <HeartFilled /> : <HeartOutlined />}
                                onClick={onListClick(list)}
                            />,
                        ]}>
                        {list.name ?? `Example list name ${ix}`}
                    </List.Item>
                )
            })}
        </List>
    )
}
