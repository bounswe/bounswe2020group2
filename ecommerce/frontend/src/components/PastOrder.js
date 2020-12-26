import './PastOrder.less'

export const PastOrder = ({ order, mode }) => {
    return JSON.stringify(order, null, 4)
}
