import Cards from 'react-credit-cards';
import 'react-credit-cards/es/styles-compiled.css'
import './CreditCard.less'
import cls from 'classnames'
import { DeleteOutlined, EditOutlined } from '@ant-design/icons'
import { useState } from 'react';
import { EditCardModal } from './EditCardModal';
import { Popconfirm } from 'antd';
export const CreditCard = ({   
        cardProps = {},
        cardId = {}, 
        cardName = "My Card", 
        selected = false,
        onSelect = () => {}}) => {
    
    const [editVisible, setEditVisible] = useState(false);
    return <div onClick={()=>onSelect(cardId)} className={cls('creditcard-container', {
        'creditcard-container__selected': selected,
        })}>
        <div className="creditcard-header">
            <input type="radio" name="selectedCard" checked={selected}  value={cardId} onChange={(val)=>onSelect(val.target.value)} />
            &nbsp;{cardName}
            <div className="creditcard-header-icons">
                <EditCardModal setVisible={setEditVisible} visible={editVisible} cardProps={cardProps} cardId={cardId} cardName={cardName} />
                <EditOutlined onClick={()=>{setEditVisible(true)}} /> &nbsp;
                <Popconfirm
                    title="Delete this card?"
                    onConfirm={()=>{}}
                    onCancel={()=>{}}
                    okText="Yes"
                    cancelText="No"
                > 
                    <DeleteOutlined />
                </Popconfirm>
            </div>
        </div>
        <div className="creditcard-card">
            <Cards className="creditcard-card" {...cardProps} />
        </div>
    </div>
}