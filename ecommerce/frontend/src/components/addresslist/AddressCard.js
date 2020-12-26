import './AddressCard.less'
import cls from 'classnames'
import { DeleteOutlined, EditOutlined } from '@ant-design/icons'
import { useState } from 'react'
import { AddressModal } from './AddressModal'
import { Popconfirm, Spin, notification } from 'antd'
import { sleep } from '../../utils'
import { api } from '../../api'
import { useAppContext } from '../../context/AppContext'

export const AddressCard = ({ address, selected = false, onSelect = () => {}, onAddressInfoChange = () => {} }) => {
    const [editVisible, setEditVisible] = useState(false)
    const [isLoading, setIsLoading] = useState(false)
    const { user } = useAppContext()

    const onDelete = async () => {
        setIsLoading(true)
        try {
            const {
                data: { status },
            } = await api.delete(`/customer/${user.id}/addresses/${address.id}`)
            if (status.successful) {
                notification.success({ message: status.message })
                onAddressInfoChange()
            } else {
                notification.warning({ message: status.message })
            }
        } catch (error) {
            notification.warning({ message: 'There was an error with your request.' })
            console.error(error)
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <div
            onClick={() => onSelect(address?.id)}
            className={cls('address-container', {
                'address-container__selected': selected,
            })}>
            <Spin spinning={isLoading}>
                <div className="address-header">
                    <input
                        type="radio"
                        name="selectedAddress"
                        checked={selected}
                        value={address.id}
                        onChange={val => onSelect(val.target.value)}
                    />
                    &nbsp;{address.title}
                    <div className="address-header-icons">
                        <AddressModal
                            onCancel={() => setEditVisible(false)}
                            onSuccess={() => {
                                setEditVisible(false)
                                onAddressInfoChange()
                            }}
                            mode="edit"
                            visible={editVisible}
                            address={address}
                        />
                        <EditOutlined
                            onClick={() => {
                                setEditVisible(true)
                            }}
                        />{' '}
                        &nbsp;
                        <Popconfirm title="Delete this address?" onConfirm={onDelete} okText="Yes" cancelText="No">
                            <DeleteOutlined />
                        </Popconfirm>
                    </div>
                </div>
                <div className="address-card">
                    <div className="address-card-name">{address.name + ' ' + address.surname}</div>
                    <div className="address-card-address">{address.address}</div>
                    <div className="address-card-zipcode">{address.zip_code}</div>
                    <div className="address-card-region">
                        {[address.province, address.city, address.country].filter(Boolean).join(' / ')}
                    </div>
                    <div className="address-card-phone">
                        {[address.phone.country_code, address.phone.number].filter(Boolean).join('')}
                    </div>
                </div>
            </Spin>
        </div>
    )
}
