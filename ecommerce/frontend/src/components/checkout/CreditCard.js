import Cards from 'react-credit-cards';
import 'react-credit-cards/es/styles-compiled.css'
import './CreditCard.less'
import cls from 'classnames'
import { DeleteOutlined, EditOutlined } from '@ant-design/icons'

export const CreditCard = ({   
        cardProps = {},
        cardId = {}, 
        cardName = "My Card", 
        selected = false,
        onSelect = () => {}}) => {

    return <div onClick={()=>onSelect(cardId)} className={cls('creditcard-container', {
        'creditcard-container__selected': selected,
        })}>
        <div className="creditcard-header">
            <input type="radio" name="selectedCard" checked={selected}  value={cardId} onChange={(val)=>onSelect(val.target.value)} />
            &nbsp;{cardName}
            <div className="creditcard-header-icons">
                <EditOutlined /> &nbsp;
                <DeleteOutlined />
            </div>
        </div>
        <div className="creditcard-card">
            <Cards className="creditcard-card" {...cardProps}/>
        </div>
    </div>
}