import './Layout_common.less'
import { Layout } from 'antd'

export const Footer = () => {
    const cols = [
        [['About us'], ['My Account'], ['Orders History']],
        [['Contact us'], ['Address'], ['Email'], ['Working Days']],
        [['Follow Us'], ['Facebook'], ['Twitter'], ['Instagram']],
    ]

    const renderCol = (col, ix1) => {
        const renderEntry = (entry, ix2) => {
            return <p key={ix2}>{entry}</p>
        }

        return (
            <div key={ix1} className="footer-col">
                {col.map(renderEntry)}
            </div>
        )
    }

    return <Layout.Footer className="footer">{cols.map(renderCol)}</Layout.Footer>
}
