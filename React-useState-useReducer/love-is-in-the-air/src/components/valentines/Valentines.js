import { useState, useEffect, useReducer } from "react";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";

import Modal from "react-bootstrap/Modal";
import Card from "react-bootstrap/Card";
import Image from "react-bootstrap/Image";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

function valentineReducer(valentines, action) {
  switch(action.type) {
    case "get":
      return [...action.valentines];
    case "edit":
      return valentines.map(valentine => {
        if (valentine.id === action.valentine.id) {
          return action.valentine;
        } else {
          return valentine;
        }
      });
    case "delete":
      return valentines.filter(valentine => valentine.id !== action.valentineId);
  }
}

function Valentines({ messages, setMessages, makeId }) {
  // const [valentines, setValentines] = useState([]);
  const [valentines, dispatch] = useReducer(valentineReducer, []);

  const [editModalShow, setEditModalShow] = useState(false);
  const [deleteModalShow, setDeleteModalShow] = useState(false);
  const [deleteValentineId, setDeleteValentineId] = useState(0);

  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm();

  useEffect(() => {
    getValentines();
  }, []);

  const getValentines = () => {
    fetch("http://localhost:4000/valentines")
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          setMessages([
            ...messages,
            {
              id: makeId(),
              type: "failure",
              text: response.statusText,
            },
          ]);
          navigate("/");
        }
      })
      // .then((data) => setValentines(data))
      .then((data) => {
        dispatch({type: "get", valentines: data}) // Dispatch takes an 'action' object as an argument. Actions must have a 'type' property, and then a property for data
      })
      .catch((errors) =>
        setMessages([
          ...messages,
          { id: makeId(), type: "failure", text: errors },
        ])
      );
  };

  const showEditModal = (valentine) => {
    setValue("id", valentine.id);
    setValue("valentineMessage", valentine.valentineMessage);
    setValue("valentineSource", valentine.valentineSource);
    setValue("valentineCategory", valentine.valentineCategory);
    setEditModalShow(true);
  }

  const handleEdit = (valentineData) => {
    fetch("http://localhost:4000/valentines/" + valentineData.id, {
      method: "PUT",
      headers: {
          "Content-Type": "application/json"
      },
      body: JSON.stringify(valentineData)
    })
    .then(response => {
        if (response.status === 404) {
          setMessages([...messages, { id: makeId(), type: "failure", text: response.statusText }]);
        }
        if (response.status === 200) {
          setMessages([...messages, { id: makeId(), type: "success", text: "Valentine was successfully edited." }])
          // const editedValentines = valentines.map(valentine => {
          //   if (valentine.id === valentineData.id) {
          //     return valentineData;
          //   } else {
          //     return valentine;
          //   }
          // });
          // setValentines(editedValentines);
          dispatch({type: "edit", valentine: valentineData});
        }
        setEditModalShow(false);
    })
    .catch(error => setMessages([...messages, { id: makeId(), type: "failure", text: error.message }]));
  };

  const showDeleteModal = (valentine) => {
    setDeleteModalShow(true)
    setDeleteValentineId(valentine.id)
  }

  const handleDelete = () => {
    fetch("http://localhost:4000/valentines/" + deleteValentineId, {   
      method: "DELETE"
    })
    .then(response => {
        if (response.status == 404) {
          setMessages([...messages, { id: makeId(), type: "failure", text: response.statusText }]);
        }
        if (response.status == 200) {
          setMessages([...messages, { id: makeId(), type: "success", text: "Valentine was successfully deleted." }])
          // const filteredValentines = valentines.filter(valentine => valentine.id !== deleteValentineId);
          // setValentines(filteredValentines);
          dispatch({type: "delete", valentineId: deleteValentineId})
        }
        setDeleteModalShow(false);
        setDeleteValentineId(0);
    })
    .catch(error => setMessages([...messages, { id: makeId(), type: "failure", text: error.message }]));
  }

  const colourCoder = (valentine) => {
    let baseClass = "valentine-card card-scalloped ";

    if (valentine.valentineCategory == "Funny") {
      return baseClass + "funny";
    } else if (valentine.valentineCategory == "Sentimental") {
      return baseClass + "sentimental";
    } else if (valentine.valentineCategory == "Cynical") {
      return baseClass + "cynical";
    } else {
      return baseClass;
    }
  };

  const createValentines = () => {
    return valentines.map((valentine) => {
      return (
        <Col key={"valentine-" + valentine.id} className="mb-5" sm={{ span: 4 }}>
          <Card className={colourCoder(valentine)}>
            <Row>
              <Col sm={{ span: 2 }}>
                <Image
                  fluid
                  className="ms-3 valentine-card-img"
                  src={
                    process.env.PUBLIC_URL +
                    "/images/bubble-gum-pink-heart-with-highlight-1.png"
                  }
                  alt="Shiny pink heart at an angle"
                />
              </Col>
              <Col sm={{ span: 10 }}>
                <Card.Body className="valentine-card-body">
                  <h5 className="m-0">{valentine.valentineMessage}</h5>
                  <p className="text-end opacity-50 fst-italic fs-6 mb-0">
                    &mdash; {valentine.valentineSource}
                  </p>
                  <p className="text-end valentine-category opacity-50">
                    #{valentine.valentineCategory}
                  </p>
                  <div className="valentine-buttons">
                    <Button
                      size="sm"
                      variant="secondary"
                      onClick={() => showEditModal(valentine)}
                    >
                      Edit
                    </Button>
                    <Button
                      size="sm"
                      variant="danger"
                      className="ms-2"
                      onClick={() => showDeleteModal(valentine)}
                    >
                      Delete
                    </Button>
                  </div>
                </Card.Body>
              </Col>
            </Row>
          </Card>
        </Col>
      );
    });
  };

  return (
    <>
      <Row className="gx-5">{createValentines()}</Row>
      <Modal
        centered
        size="lg"
        show={editModalShow}
        onHide={() => setEditModalShow(false)}
      >
        <Modal.Header closeButton>
          <Modal.Title id="contained-modal-title-vcenter">
            Edit Valentine
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Row>
            <Col sm={{ span: 7 }}>
              <Form id="valentine-form" onSubmit={handleSubmit(handleEdit)}>
                <Form.Group hidden>
                  <Form.Control
                    type="text"
                    id="valentine-id"
                    {...register("id")}
                  />
                </Form.Group>

                <Form.Group>
                  <Form.Label htmlFor="valentine-message">Message</Form.Label>
                  <Form.Control
                    as="textarea"
                    id="valentine-message"
                    {...register("valentineMessage", {
                      required: "Must have a message.",
                    })}
                  />
                  <Form.Text className="form-error-message">
                    {errors.valentineMessage?.message}
                  </Form.Text>
                </Form.Group>

                <Form.Group>
                  <Form.Label className="mt-3" htmlFor="valentine-message">
                    Source
                  </Form.Label>
                  <Form.Control
                    type="text"
                    id="valentine-source"
                    autoComplete="off"
                    {...register("valentineSource", {
                      required: "Must have a source.",
                    })}
                  />
                  <Form.Text className="form-error-message">
                    {errors.valentineSource?.message}
                  </Form.Text>
                </Form.Group>

                <Form.Group>
                  <Form.Label className="mt-3" htmlFor="valentine-category">
                    Category
                  </Form.Label>
                  <Form.Select
                    id="valentine-category"
                    aria-label="Select a category..."
                    {...register("valentineCategory", {
                      required: "Must have a category.",
                    })}
                  >
                    <option value="" disabled>
                      Select a category...
                    </option>
                    <option value="Funny">Funny</option>
                    <option value="Sentimental">Sentimental</option>
                    <option value="Cynical">Cynical</option>
                  </Form.Select>
                  <Form.Text className="form-error-message">
                    {errors.valentineCategory?.message}
                  </Form.Text>
                </Form.Group>

                <Button variant="primary" className="mt-3" type="submit">
                  Edit
                </Button>
                <Button
                  variant="secondary"
                  className="mt-3 ms-2"
                  type="button"
                  onClick={() => setEditModalShow(false)}
                >
                  Cancel
                </Button>
              </Form>
            </Col>
            <Col sm={{ span: 5 }}>
              <Image
                fluid
                className="mt-1 mb-4 form-image"
                src={
                  process.env.PUBLIC_URL +
                  "/images/bubble-gum-woman-with-a-big-magnet-attracts-likes.png"
                }
                alt="A woman uses a magnet to attract small pink hearts towards her"
              />
            </Col>
          </Row>
        </Modal.Body>
      </Modal>

      <Modal
        centered
        size="md"
        show={deleteModalShow}
        onHide={() => setDeleteModalShow(false)}
      >
        <Modal.Body className="text-center">
          <p className="h5">Are you sure you want to delete this Valentine?</p>
          <Button variant="primary" className="mt-3" onClick={handleDelete}>
            Delete
          </Button>
          <Button
            variant="secondary"
            className="mt-3 ms-2"
            type="button"
            onClick={() => setDeleteModalShow(false)}
          >
            Cancel
          </Button>
        </Modal.Body>
      </Modal>
    </>
  );
}

export default Valentines;
