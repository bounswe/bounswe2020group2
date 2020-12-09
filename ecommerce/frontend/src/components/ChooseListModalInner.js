import './ChooseListModalInner.less'

import React, { useEffect, useState } from 'react'
import { Button, List } from 'antd'
import { sleep } from '../utils'
import { HeartOutlined, HeartFilled } from '@ant-design/icons'

export const ChooseListModalInner = ({ onChooseList }) => {
    const [lists, setLists] = useState([])
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        async function fetch() {
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

        fetch()
    }, [])

    const onListClick = list => () => onChooseList(list)

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
