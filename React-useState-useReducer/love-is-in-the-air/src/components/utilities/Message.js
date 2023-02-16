import Alert from 'react-bootstrap/Alert';
import CloseButton from 'react-bootstrap/CloseButton';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

function Message({ message, messages, setMessages }) {

    const handleClose = () => {
        let filteredMessages = messages.filter(m => m.id !== message.id);
        setMessages(filteredMessages);        
    }

    setTimeout(()=>{
        handleClose();
    }, 5000)

    return (
        <Alert variant={message.type === "success" ? "success" : "danger"}>
            <Row>
                <Col xs={11}>
                    <p className="mb-0">{message.text}</p>
                </Col>
                <Col xs={1} className="text-end">
                    <CloseButton type="button" aria-label="Close" onClick={handleClose}></CloseButton>
                </Col>
            </Row>
        </Alert>
    )
}

export default Message;