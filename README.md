# Restaurant Recommendation System - Architecture Diagram

## System Architecture Overview

This document provides a comprehensive architecture diagram for the Restaurant Recommendation System for Runners.

## High-Level Architecture

```mermaid
graph TB
    subgraph "Client Layer"
        Browser[Web Browser]
        Mobile[Mobile Browser]
    end
    
    subgraph "Frontend Layer - React Application"
        subgraph "UI Components"
            Pages[Pages<br/>Home, Recommendations, Search, Profile]
            AuthComp[Auth Components<br/>Login, Register, ProtectedRoute]
            CommonComp[Common Components<br/>Header, Footer]
            SearchComp[Search Components<br/>QuickSearch, AdvancedSearch, Map]
        end
        
        subgraph "State Management"
            AuthContext[AuthContext<br/>JWT Token Management]
            LocalState[Local State<br/>React Hooks]
        end
        
        subgraph "Services"
            APIService[RestaurantSearchAPI<br/>Axios HTTP Client]
            UserService[UserDataService]
        end
        
        subgraph "Routing"
            Router[React Router<br/>Client-side Routing]
        end
    end
    
    subgraph "Backend Layer - Spring Boot Application"
        subgraph "API Gateway / Controllers"
            AuthController[AuthController<br/>/api/auth/*]
            RestaurantController[RestaurantController<br/>/api/restaurants/*]
            UserController[UserController<br/>/api/users/*]
        end
        
        subgraph "Security Layer"
            SecurityConfig[SecurityConfig<br/>CORS, Authentication]
            JwtFilter[JwtAuthenticationFilter<br/>Token Validation]
            RateLimitFilter[RateLimitingFilter<br/>Request Throttling]
            UserDetailsService[CustomUserDetailsService<br/>User Authentication]
        end
        
        subgraph "Business Logic / Services"
            RestaurantService[RestaurantService<br/>Recommendation Engine<br/>SPARQL Queries]
            UserService[UserService<br/>User Management]
            JwtUtil[JwtUtil<br/>Token Generation]
            ValidationUtil[ValidationUtil<br/>Input Validation]
        end
        
        subgraph "Semantic Reasoning"
            JenaModel[Apache Jena Model<br/>RDF Ontology Loading]
            Reasoner[Rule Reasoner<br/>Hybrid Reasoning]
            SPARQL[SPARQL Query Engine<br/>Recommendation Queries]
        end
        
        subgraph "Data Access Layer"
            RestaurantRepo[RestaurantRepository<br/>JPA Repository]
            UserRepo[UserRepository<br/>JPA Repository]
        end
        
        subgraph "Configuration"
            DataLoader[DataLoader<br/>Sample Data]
            SwaggerConfig[SwaggerConfig<br/>API Documentation]
            WebConfig[WebServiceConfig<br/>CORS Configuration]
        end
    end
    
    subgraph "Data Layer"
        H2DB[(H2 In-Memory Database<br/>Users, Restaurants)]
        RDFOntology[(RDF Ontology File<br/>RestaurantOntology.rdf)]
        RulesFile[(Rule File<br/>rule.rules)]
    end
    
    subgraph "External Services"
        SwaggerUI[Swagger UI<br/>API Documentation]
        H2Console[H2 Console<br/>Database Management]
    end
    
    Browser --> Pages
    Mobile --> Pages
    Pages --> Router
    Router --> AuthComp
    Router --> CommonComp
    Router --> SearchComp
    
    AuthComp --> AuthContext
    Pages --> AuthContext
    Pages --> APIService
    Pages --> UserService
    APIService --> AuthContext
    
    APIService -->|HTTP/REST| AuthController
    APIService -->|HTTP/REST| RestaurantController
    APIService -->|HTTP/REST| UserController
    
    AuthController --> JwtFilter
    RestaurantController --> JwtFilter
    UserController --> JwtFilter
    
    JwtFilter --> SecurityConfig
    RateLimitFilter --> SecurityConfig
    JwtFilter --> UserDetailsService
    
    AuthController --> UserService
    AuthController --> JwtUtil
    
    RestaurantController --> RestaurantService
    UserController --> UserService
    
    RestaurantService --> JenaModel
    RestaurantService --> Reasoner
    RestaurantService --> SPARQL
    RestaurantService --> RestaurantRepo
    
    UserService --> UserRepo
    UserService --> ValidationUtil
    
    JenaModel --> RDFOntology
    Reasoner --> RulesFile
    SPARQL --> JenaModel
    
    RestaurantRepo --> H2DB
    UserRepo --> H2DB
    
    DataLoader --> H2DB
    DataLoader --> RDFOntology
    
    SwaggerConfig --> SwaggerUI
    H2DB --> H2Console
    
    style Browser fill:#e1f5ff
    style Mobile fill:#e1f5ff
    style Pages fill:#fff4e6
    style AuthContext fill:#fff4e6
    style APIService fill:#fff4e6
    style AuthController fill:#f3e5f5
    style RestaurantController fill:#f3e5f5
    style UserController fill:#f3e5f5
    style SecurityConfig fill:#ffebee
    style JwtFilter fill:#ffebee
    style RestaurantService fill:#e8f5e9
    style UserService fill:#e8f5e9
    style JenaModel fill:#e8f5e9
    style H2DB fill:#fff9c4
    style RDFOntology fill:#fff9c4
```

## Detailed Component Architecture

### Frontend Architecture

```mermaid
graph LR
    subgraph "React Frontend (Port 3000)"
        subgraph "Presentation Layer"
            A[App.js<br/>Main Application]
            B[Pages<br/>Home, Recommendations, Search, Profile, Detail]
            C[Components<br/>Auth, Common, Search]
        end
        
        subgraph "State & Context"
            D[AuthContext<br/>Authentication State]
            E[Local State<br/>Component State]
        end
        
        subgraph "Service Layer"
            F[RestaurantSearchAPI<br/>Axios Client]
            G[UserDataService<br/>User Operations]
        end
        
        subgraph "Routing"
            H[React Router<br/>Navigation]
        end
        
        A --> B
        A --> C
        B --> D
        C --> D
        B --> F
        B --> G
        A --> H
    end
    
    style A fill:#6366f1,color:#fff
    style B fill:#8b5cf6,color:#fff
    style C fill:#ec4899,color:#fff
    style D fill:#10b981,color:#fff
    style F fill:#f59e0b,color:#fff
```

### Backend Architecture

```mermaid
graph TB
    subgraph "Spring Boot Backend (Port 8080)"
        subgraph "REST API Layer"
            RC[RestaurantController]
            AC[AuthController]
            UC[UserController]
        end
        
        subgraph "Security & Filters"
            SC[SecurityConfig]
            JF[JwtAuthenticationFilter]
            RLF[RateLimitingFilter]
            CUDS[CustomUserDetailsService]
        end
        
        subgraph "Service Layer"
            RS[RestaurantService<br/>- Ontology Loading<br/>- Rule Reasoning<br/>- SPARQL Queries<br/>- Recommendation Logic]
            US[UserService<br/>- User CRUD<br/>- Authentication]
            JU[JwtUtil<br/>- Token Generation<br/>- Token Validation]
            VU[ValidationUtil<br/>- Input Validation]
        end
        
        subgraph "Data Access"
            RR[RestaurantRepository<br/>JPA]
            UR[UserRepository<br/>JPA]
        end
        
        subgraph "Semantic Layer"
            JM[Jena Model<br/>RDF Ontology]
            RE[Rule Reasoner<br/>Hybrid Mode]
            SQ[SPARQL Engine]
        end
        
        subgraph "Configuration"
            DL[DataLoader<br/>Initial Data]
            SW[SwaggerConfig<br/>API Docs]
        end
        
        RC --> RS
        AC --> US
        UC --> US
        
        RC --> JF
        AC --> JF
        UC --> JF
        
        JF --> SC
        RLF --> SC
        JF --> CUDS
        
        RS --> JM
        RS --> RE
        RS --> SQ
        RS --> RR
        
        US --> UR
        US --> JU
        US --> VU
        
        RE --> JM
        SQ --> JM
        
        DL --> RR
        DL --> UR
        DL --> JM
    end
    
    style RC fill:#4f46e5,color:#fff
    style AC fill:#4f46e5,color:#fff
    style UC fill:#4f46e5,color:#fff
    style RS fill:#10b981,color:#fff
    style US fill:#10b981,color:#fff
    style JM fill:#f59e0b,color:#fff
```

## Data Flow Architecture

### Authentication Flow

```mermaid
sequenceDiagram
    participant U as User
    participant F as Frontend
    participant AC as AuthController
    participant US as UserService
    participant DB as H2 Database
    participant JWT as JwtUtil
    
    U->>F: Login Request
    F->>AC: POST /api/auth/login
    AC->>US: authenticate()
    US->>DB: Find User
    DB-->>US: User Data
    US->>JWT: Generate Token
    JWT-->>US: JWT Token
    US-->>AC: AuthResponse
    AC-->>F: JWT Token
    F->>F: Store in AuthContext
    F-->>U: Authenticated
```

### Recommendation Flow

```mermaid
sequenceDiagram
    participant U as User
    participant F as Frontend
    participant RC as RestaurantController
    participant RS as RestaurantService
    participant JM as Jena Model
    participant RE as Rule Reasoner
    participant SQ as SPARQL Engine
    participant DB as H2 Database
    
    U->>F: Request Recommendations
    F->>RC: POST /api/restaurants/recommendations
    RC->>RS: getRestaurantRecommendations()
    RS->>JM: Load RDF Ontology
    JM-->>RS: Ontology Model
    RS->>JM: Create User Instance
    RS->>RE: Apply Rules (Hybrid)
    RE->>JM: Infer Recommendations
    RS->>SQ: Execute SPARQL Query
    SQ->>JM: Query Inferred Model
    JM-->>SQ: Restaurant URIs + Confidence
    RS->>DB: Fetch Restaurant Details
    DB-->>RS: Restaurant Data
    RS->>RS: Calculate Match Scores
    RS-->>RC: Sorted Recommendations
    RC-->>F: JSON Response
    F-->>U: Display Results
```

## Technology Stack

### Frontend Stack
- **React 19.1.1** - UI Library
- **Material-UI 7.3.1** - Component Library
- **React Router 7.8.2** - Routing
- **React Hook Form 7.62.0** - Form Management
- **Axios 1.11.0** - HTTP Client
- **Yup 1.7.0** - Schema Validation
- **React Leaflet** - Map Integration

### Backend Stack
- **Spring Boot 3.4.4** - Framework
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - Data Persistence
- **H2 Database** - In-Memory Database
- **Apache Jena 4.3.2** - Semantic Web & RDF
- **JWT (jjwt 0.11.5)** - Token Authentication
- **Swagger/OpenAPI** - API Documentation

## Security Architecture

```mermaid
graph TB
    subgraph "Security Layers"
        A[Rate Limiting Filter<br/>Request Throttling]
        B[JWT Authentication Filter<br/>Token Validation]
        C[Spring Security Config<br/>CORS, Authentication Rules]
        D[Custom User Details Service<br/>User Authentication]
        E[Password Encoder<br/>BCrypt]
    end
    
    Request[Incoming Request] --> A
    A -->|Valid| B
    A -->|Rate Limited| Reject[429 Too Many Requests]
    B -->|No Token| Public[Public Endpoints]
    B -->|Invalid Token| Unauthorized[401 Unauthorized]
    B -->|Valid Token| C
    C --> D
    D --> E
    D -->|Authenticated| Protected[Protected Endpoints]
    
    style A fill:#ffebee
    style B fill:#ffebee
    style C fill:#ffebee
    style D fill:#e8f5e9
    style E fill:#e8f5e9
```

## API Endpoints Architecture

```mermaid
graph LR
    subgraph "Public Endpoints"
        P1[GET /api/restaurants/all]
        P2[GET /api/restaurants/search]
        P3[GET /api/restaurants/{id}]
        P4[POST /api/auth/register]
        P5[POST /api/auth/login]
    end
    
    subgraph "Protected Endpoints"
        PR1[POST /api/restaurants/recommendations]
        PR2[GET /api/restaurants/search/advanced]
        PR3[GET /api/users]
        PR4[GET /api/users/{id}]
        PR5[PUT /api/users/{id}]
    end
    
    subgraph "Admin Endpoints"
        A1[DELETE /api/users/{id}]
        A2[POST /api/users]
    end
    
    P1 --> JWT[JWT Filter]
    P2 --> JWT
    P3 --> JWT
    P4 --> JWT
    P5 --> JWT
    
    PR1 --> JWT
    PR2 --> JWT
    PR3 --> JWT
    PR4 --> JWT
    PR5 --> JWT
    
    A1 --> JWT
    A2 --> JWT
    
    JWT -->|Valid| Allow[Allow Request]
    JWT -->|Invalid| Deny[Deny Request]
    
    style P1 fill:#e8f5e9
    style P2 fill:#e8f5e9
    style P3 fill:#e8f5e9
    style PR1 fill:#fff9c4
    style PR2 fill:#fff9c4
    style A1 fill:#ffebee
```

## Semantic Reasoning Architecture

```mermaid
graph TB
    subgraph "Semantic Layer"
        A[RDF Ontology File<br/>RestaurantOntology.rdf]
        B[Rule File<br/>rule.rules]
        C[Jena Model Factory<br/>Load Ontology]
        D[Rule Reasoner<br/>Hybrid Mode]
        E[Inferred Model<br/>InfModel]
        F[SPARQL Query<br/>SELECT ?restaurant ?confidence]
        G[Query Results<br/>Restaurant URIs + Scores]
    end
    
    A --> C
    B --> D
    C --> D
    D --> E
    E --> F
    F --> G
    
    subgraph "User Request"
        H[User Preferences<br/>Runner Type, Budget, Nutrition]
    end
    
    H --> C
    C --> I[Create User Instance<br/>in RDF Model]
    I --> D
    
    G --> J[Match with Database<br/>Fetch Restaurant Details]
    J --> K[Calculate Match Scores<br/>Sort by Confidence]
    
    style A fill:#f59e0b
    style B fill:#f59e0b
    style D fill:#10b981
    style E fill:#10b981
    style F fill:#6366f1
```

## Deployment Architecture

```mermaid
graph TB
    subgraph "Development Environment"
        FE[React Dev Server<br/>localhost:3000]
        BE[Spring Boot App<br/>localhost:8080]
        H2[H2 In-Memory DB<br/>Embedded]
    end
    
    subgraph "Production Environment (Future)"
        LB[Load Balancer]
        FE1[React Build<br/>Static Files]
        FE2[React Build<br/>Static Files]
        BE1[Spring Boot<br/>Instance 1]
        BE2[Spring Boot<br/>Instance 2]
        DB[(PostgreSQL/MySQL<br/>Production DB)]
        Cache[(Redis Cache<br/>Optional)]
    end
    
    FE --> BE
    BE --> H2
    
    LB --> FE1
    LB --> FE2
    FE1 --> BE1
    FE2 --> BE2
    BE1 --> DB
    BE2 --> DB
    BE1 --> Cache
    BE2 --> Cache
    
    style FE fill:#e1f5ff
    style BE fill:#f3e5f5
    style H2 fill:#fff9c4
    style DB fill:#fff9c4
    style Cache fill:#ffebee
```

## Component Interaction Diagram

```mermaid
graph TB
    subgraph "User Interface"
        UI[User Interface<br/>React Components]
    end
    
    subgraph "API Layer"
        API[REST Controllers<br/>JSON API]
    end
    
    subgraph "Business Logic"
        BL[Services<br/>Business Rules]
    end
    
    subgraph "Data Sources"
        DB[(Relational Data<br/>H2 Database)]
        ONT[(Semantic Data<br/>RDF Ontology)]
    end
    
    UI -->|HTTP Requests| API
    API -->|Service Calls| BL
    BL -->|JPA Queries| DB
    BL -->|SPARQL Queries| ONT
    BL -->|Reasoning| ONT
    ONT -->|Inferred Data| BL
    DB -->|Entity Data| BL
    BL -->|Business Objects| API
    API -->|JSON Response| UI
    
    style UI fill:#e1f5ff
    style API fill:#f3e5f5
    style BL fill:#e8f5e9
    style DB fill:#fff9c4
    style ONT fill:#f59e0b
```

## Key Architectural Patterns

1. **Layered Architecture**: Clear separation between Presentation, Business Logic, and Data layers
2. **RESTful API**: Stateless REST endpoints for frontend-backend communication
3. **Semantic Web**: RDF ontology for intelligent reasoning and recommendations
4. **Security First**: JWT-based authentication with rate limiting
5. **Component-Based Frontend**: Modular React components with context-based state management
6. **Service-Oriented Backend**: Service layer encapsulates business logic
7. **Repository Pattern**: Data access abstraction through JPA repositories

## Performance Considerations

- **Caching**: Ontology model caching in RestaurantService
- **Rate Limiting**: API protection against abuse
- **Lazy Loading**: Frontend code splitting
- **Database Indexing**: JPA automatic indexing
- **SPARQL Optimization**: Efficient query patterns

## Scalability Considerations

- **Stateless Backend**: JWT tokens enable horizontal scaling
- **In-Memory Database**: H2 suitable for development, production needs PostgreSQL/MySQL
- **Semantic Reasoning**: Can be optimized with caching strategies
- **Frontend CDN**: Static assets can be served via CDN
- **API Gateway**: Can add API gateway for production deployment

