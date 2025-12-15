import styled from "styled-components";
import { Link } from "react-router-dom";

export const Container = styled.div`
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem 0;
`;

export const BackButton = styled(Link)`
  display: inline-block;
  color: var(--text-sub, #666);
  text-decoration: none;
  margin-bottom: 2rem;
  font-weight: 500;
  
  &:hover {
    color: var(--primary, #007bff);
  }
`;

export const Title = styled.h1`
  margin-bottom: 2rem;
  color: var(--text-main, #333);
  font-size: 2rem;
  font-weight: 700;
`;

export const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  background: white;
  padding: 2.5rem;
  border-radius: 16px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05);
  border: 1px solid var(--border, #e2e8f0);
`;

export const FormGroup = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Label = styled.label`
  margin-bottom: 0.5rem;
  color: var(--text-main, #333);
  font-weight: 600;
  font-size: 0.95rem;
`;

export const RequiredMark = styled.span`
  color: #ef4444;
  margin-left: 4px;
`;

export const Input = styled.input`
  padding: 0.8rem;
  border: 1px solid var(--border, #ddd);
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.2s;
  
  &:focus {
    outline: none;
    border-color: var(--primary, #007bff);
    box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
  }
`;

export const Select = styled.select`
  padding: 0.8rem;
  border: 1px solid var(--border, #ddd);
  border-radius: 8px;
  font-size: 1rem;
  background-color: white;
  transition: border-color 0.2s;
  cursor: pointer;

  &:focus {
    outline: none;
    border-color: var(--primary, #007bff);
  }
`;

export const Textarea = styled.textarea`
  padding: 0.8rem;
  border: 1px solid var(--border, #ddd);
  border-radius: 5px;
  font-size: 1rem;
  min-height: 200px;
  resize: vertical;
  font-family: inherit;
  transition: border-color 0.2s;
  
  &:focus {
    outline: none;
    border-color: var(--primary, #007bff);
    box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
  }
`;

export const Button = styled.button`
  padding: 1rem 2rem;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1.1rem;
  font-weight: 600;
  background-color: var(--primary, #4f46e5);
  color: white;
  margin-top: 1rem;
  transition: background-color 0.2s;
  
  &:hover {
    background-color: var(--primary-dark, #4338ca);
  }
`;